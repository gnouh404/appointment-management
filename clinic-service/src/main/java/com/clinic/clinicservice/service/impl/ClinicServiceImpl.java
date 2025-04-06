package com.clinic.clinicservice.service.impl;

import com.clinic.clinicservice.entity.Clinic;
import com.clinic.clinicservice.repository.ClinicRepository;
import com.clinic.clinicservice.service.ClinicService;
import com.example.commonlibrary.dto.request.ClinicCreateRequest;
import com.example.commonlibrary.dto.request.ClinicUpdateRequest;
import com.example.commonlibrary.dto.response.ClinicResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClinicServiceImpl implements ClinicService {

    private final ClinicRepository clinicRepository;

    @Override
    @Transactional
    public ClinicResponse createClinic(ClinicCreateRequest request) {
        Clinic clinic = new Clinic();
        clinic.setName(request.name());
        clinic.setAddress(request.address());
        clinic.setPhone(request.phone());
        clinic.setCreatedAt(LocalDateTime.now());
        clinic.setUpdatedAt(LocalDateTime.now());

        Clinic savedClinic = clinicRepository.save(clinic);
        return convertToResponse(savedClinic);
    }

    @Override
    public ClinicResponse getClinic(UUID id) {
        Clinic clinic = clinicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clinic not found"));

        return convertToResponse(clinic);
    }

    @Override
    public List<ClinicResponse> getAllClinics() {
        return clinicRepository.findAll().stream()
                .map(this::convertToResponse)
                .toList();
    }

    @Override
    @Transactional
    public ClinicResponse updateClinic(UUID id, ClinicUpdateRequest request) {
        Clinic clinic = clinicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clinic not found"));

        if (request.name() != null) {
            clinic.setName(request.name());
        }
        if (request.address() != null) {
            clinic.setAddress(request.address());
        }
        if (request.phone() != null) {
            clinic.setPhone(request.phone());
        }

        Clinic updatedClinic = clinicRepository.save(clinic);
        return convertToResponse(updatedClinic);
    }

    @Override
    @Transactional
    public void deleteClinic(UUID id) {
        clinicRepository.deleteById(id);
    }

    private ClinicResponse convertToResponse(Clinic clinic) {
        // Get list of doctor Ids
        List<UUID> doctorIds = new ArrayList<>();
        if (clinic.getDoctorClinics() != null) {
            doctorIds = clinic.getDoctorClinics().stream()
                    .map(dc -> dc.getDoctor().getId())
                    .toList();
        }

        return new ClinicResponse(
                clinic.getName(),
                clinic.getAddress(),
                clinic.getPhone(),
                clinic.getCreatedAt(),
                clinic.getUpdatedAt(),
                doctorIds);
    }
}
