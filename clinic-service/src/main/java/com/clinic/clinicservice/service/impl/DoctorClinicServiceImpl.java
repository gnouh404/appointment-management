package com.clinic.clinicservice.service.impl;

import com.clinic.clinicservice.entity.DoctorClinic;
import com.clinic.clinicservice.entity.DoctorClinicId;
import com.clinic.clinicservice.repository.ClinicRepository;
import com.clinic.clinicservice.repository.DoctorClinicRepository;
import com.clinic.clinicservice.repository.DoctorRepository;
import com.clinic.clinicservice.service.DoctorClinicService;
import com.clinic.clinicservice.dto.DoctorClinicRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DoctorClinicServiceImpl implements DoctorClinicService {

    private final DoctorClinicRepository doctorClinicRepository;
    private final DoctorRepository doctorRepository;
    private final ClinicRepository clinicRepository;

    @Override
    @Transactional
    public void assignDoctorToClinic(DoctorClinicRequest request) {
        if (!doctorRepository.existsById(request.doctorId())) {
            throw new RuntimeException("Doctor not found");
        }
        if (!clinicRepository.existsById(request.clinicId())) {
            throw new RuntimeException("Clinic not found");
        }

        // Kiểm tra quan hệ đã tồn tại chưa
        if (doctorClinicRepository.existsById(new DoctorClinicId(request.doctorId(), request.clinicId()))) {
            throw new RuntimeException("Doctor already assigned to this clinic");
        }

        // Tạo mới DoctorClinic với embeddedId
        DoctorClinic doctorClinic = new DoctorClinic();
        doctorClinic.setId(new DoctorClinicId(request.doctorId(), request.clinicId()));

        // Set các reference
        doctorClinic.setDoctor(doctorRepository.getReferenceById(request.doctorId()));
        doctorClinic.setClinic(clinicRepository.getReferenceById(request.clinicId()));

        doctorClinicRepository.save(doctorClinic);
    }

    @Override
    @Transactional
    public void removeDoctorFromClinic(DoctorClinicRequest request) {
        DoctorClinicId id = new DoctorClinicId(request.doctorId(), request.clinicId());

        if (!doctorClinicRepository.existsById(id)) {
            throw new RuntimeException("Assignment not found");
        }

        doctorClinicRepository.deleteById(id);
    }

    @Override
    public boolean existsByDoctorAndClinic(UUID doctorId, UUID clinicId) {
        return doctorClinicRepository.existsById(new DoctorClinicId(doctorId, clinicId));
    }
}
