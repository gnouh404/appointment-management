package com.clinic.clinicservice.service.impl;

import com.clinic.clinicservice.entity.Doctor;
import com.clinic.clinicservice.repository.DoctorRepository;
import com.clinic.clinicservice.service.DoctorClinicService;
import com.clinic.clinicservice.service.DoctorService;
import com.clinic.clinicservice.dto.DoctorClinicRequest;
import com.clinic.clinicservice.dto.DoctorCreateRequest;
import com.example.commonlibrary.dto.response.clinic.DoctorResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorClinicService doctorClinicService;

    @Override
    @Transactional
    public DoctorResponse createDoctor(DoctorCreateRequest request) {
        Doctor doctor = new Doctor();
        doctor.setFullName(request.fullName());
        doctor.setSpecialty(request.specialty());
        doctor.setPhone(request.phone());
        doctor.setEmail(request.email());
        doctor.setCreatedAt(LocalDateTime.now());
        doctor.setUpdatedAt(LocalDateTime.now());

        Doctor savedDoctor = doctorRepository.save(doctor);

        // Process clinic associations if they exist
        if (request.clinicIds() != null && !request.clinicIds().isEmpty()) {
            request.clinicIds().forEach(
                    clinicId -> doctorClinicService.assignDoctorToClinic(
                            new DoctorClinicRequest(savedDoctor.getId(), clinicId)
                    )
            );
        }
        return convertToResponse(savedDoctor);
    }

    @Override
    public DoctorResponse getDoctorById(UUID id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        return convertToResponse(doctor);
    }

    @Override
    public List<DoctorResponse> getAllDoctors() {
        return doctorRepository.findAll().stream()
                .map(this::convertToResponse)
                .toList();
    }

    @Override
    @Transactional
    public DoctorResponse updateDoctor(UUID id, DoctorCreateRequest request) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        if (request.fullName() != null) {
            doctor.setFullName(request.fullName());
        }
        if (request.specialty() != null) {
            doctor.setSpecialty(request.specialty());
        }
        if (request.phone() != null) {
            doctor.setPhone(request.phone());
        }

        Doctor updatedDoctor = doctorRepository.save(doctor);
        return convertToResponse(updatedDoctor);
    }

    @Override
    @Transactional
    public void deleteDoctor(UUID id) {
        doctorRepository.deleteById(id);
    }

    private DoctorResponse convertToResponse(Doctor doctor) {

        // Get list of clinic IDs
        List<UUID> clinicIds = new ArrayList<>();
        if (doctor.getDoctorClinics() != null){
            clinicIds = doctor.getDoctorClinics().stream()
                    .map(dc -> dc.getClinic().getId())
                    .toList();
        }

        return new DoctorResponse(
                doctor.getFullName(),
                doctor.getSpecialty(),
                doctor.getPhone(),
                doctor.getEmail(),
                doctor.getUpdatedAt(),
                doctor.getCreatedAt(),
                clinicIds);

    }
}
