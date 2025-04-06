package com.appointment.appointmentservice.service.impl;

import com.appointment.appointmentservice.entity.Appointment;
import com.appointment.appointmentservice.repository.AppointmentRepository;
import com.appointment.appointmentservice.service.AppointmentService;
import com.example.commonlibrary.dto.request.AppointmentCreateRequest;
import com.example.commonlibrary.dto.request.AppointmentUpdateRequest;
import com.example.commonlibrary.dto.response.AppointmentResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository repository;
    private final RestTemplate restTemplate;

    @Override
    @Transactional
    public AppointmentResponse create(AppointmentCreateRequest request) {
        validateExternalEntities(request.patientId(), request.doctorId(), request.clinicId());
        validateNoConflict(request.doctorId(), request.startTime(), request.endTime());

        Appointment appointment = Appointment.builder()
                .patientId(request.patientId())
                .doctorId(request.doctorId())
                .clinicId(request.clinicId())
                .startTime(request.startTime())
                .endTime(request.endTime())
                .reason(request.reason())
                .status("scheduled")
                .build();

        return toResponse(repository.save(appointment));
    }

    @Override
    public AppointmentResponse getById(UUID id) {
        return toResponse(repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found")));
    }

    @Override
    public List<AppointmentResponse> getByClinicId(UUID clinicId) {
        return repository.findByClinicId(clinicId)
                .stream().map(this::toResponse).toList();
    }

    @Override
    public List<AppointmentResponse> getByDoctorId(UUID doctorId) {
        return repository.findByDoctorId(doctorId)
                .stream().map(this::toResponse).toList();
    }

    @Override
    public AppointmentResponse update(UUID id, AppointmentUpdateRequest request) {
        Appointment appointment = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        boolean shouldCheckConflict = false;

        // Nếu thay đổi thời gian hoặc bác sĩ → check trùng
        LocalDateTime newStart = request.startTime() != null ? request.startTime() : appointment.getStartTime();
        LocalDateTime newEnd = request.endTime() != null ? request.endTime() : appointment.getEndTime();

        if (request.startTime() != null || request.endTime() != null) {
            shouldCheckConflict = true;
        }

        if (request.reason() != null) {
            appointment.setReason(request.reason());
        }

        if (request.status() != null) {
            appointment.setStatus(request.status());
        }

        if (shouldCheckConflict) {
            validateNoConflict(appointment.getDoctorId(), newStart, newEnd);
            appointment.setStartTime(newStart);
            appointment.setEndTime(newEnd);
        }

        return toResponse(repository.save(appointment));
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    private AppointmentResponse toResponse(Appointment appointment) {
        return new AppointmentResponse(
                appointment.getId(),
                appointment.getPatientId(),
                appointment.getDoctorId(),
                appointment.getClinicId(),
                appointment.getStartTime(),
                appointment.getEndTime(),
                appointment.getReason(),
                appointment.getStatus()
        );
    }

    private void validateNoConflict(UUID doctorId, LocalDateTime start, LocalDateTime end) {
        List<Appointment> existing = repository.findByDoctorId(doctorId);

        for (Appointment a : existing) {
            boolean overlaps = start.isBefore(a.getEndTime()) && end.isAfter(a.getStartTime());
            if (overlaps && !"cancelled".equalsIgnoreCase(a.getStatus())) {
                throw new RuntimeException("Doctor already has appointment during this time.");
            }
        }
    }

    private void validateExternalEntities(UUID patientId, UUID doctorId, UUID clinicId) {
        try {
            restTemplate.getForObject("http://localhost:8083/api/patients/" + patientId, Object.class);
            restTemplate.getForObject("http://localhost:8082/api/doctors/" + doctorId, Object.class);
            restTemplate.getForObject("http://localhost:8082/api/clinics/" + clinicId, Object.class);
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Invalid patient/doctor/clinic ID: " + e.getMessage());
        }
    }

}
