package com.patient.patientservice.service.impl;

import com.example.commonlibrary.dto.request.PatientCreateRequest;
import com.example.commonlibrary.dto.request.PatientUpdateRequest;
import com.example.commonlibrary.dto.response.PatientResponse;
import com.patient.patientservice.client.ClinicClient;
import com.patient.patientservice.entity.Patient;
import com.patient.patientservice.repository.PatientRepository;
import com.patient.patientservice.service.PatientService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final ClinicClient clinicClient;

    @Override
    @Transactional
    public PatientResponse createPatient(PatientCreateRequest request) {
        clinicClient.validateClinicExists(request.clinicId());

        Patient patient = Patient.builder()
                .fullName(request.fullName())
                .email(request.email())
                .phone(request.phone())
                .dob(request.dob())
                .gender(request.gender())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .clinicId(request.clinicId())
                .build();

        return toResponse(patientRepository.save(patient));
    }

    @Override
    public PatientResponse getPatientById(UUID id) {
        return toResponse(patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found")));
    }

    @Override
    public List<PatientResponse> getAllPatients(UUID clinicId) {
        List<Patient> patients = clinicId != null ?
                patientRepository.findPatientByClinicId(clinicId) :
                patientRepository.findAll();

        return patients.stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional
    public PatientResponse updatePatient(UUID id, PatientUpdateRequest request) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        clinicClient.validateClinicExists(request.clinicId());

        if (request.email() != null) {
            patient.setEmail(request.email());
        }
        if (request.phone() != null) {
            patient.setPhone(request.phone());
        }
        if (request.clinicId() != null) {
            patient.setClinicId(request.clinicId());
        }

        return toResponse(patientRepository.save(patient));
    }

    @Override
    @Transactional
    public void deletePatient(UUID id) {
        patientRepository.deleteById(id);
    }

    private PatientResponse toResponse(Patient patient) {

        return PatientResponse.builder()
                .id(patient.getId())
                .fullName(patient.getFullName())
                .email(patient.getEmail())
                .phone(patient.getPhone())
                .dob(patient.getDob())
                .gender(patient.getGender())
                .clinicId(patient.getClinicId())
                .createdAt(patient.getCreatedAt())
                .updatedAt(patient.getUpdatedAt())
                .build();
    }

}
