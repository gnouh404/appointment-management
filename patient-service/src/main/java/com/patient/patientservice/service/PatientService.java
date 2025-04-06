package com.patient.patientservice.service;

import com.example.commonlibrary.dto.request.PatientCreateRequest;
import com.example.commonlibrary.dto.request.PatientUpdateRequest;
import com.example.commonlibrary.dto.response.PatientResponse;

import java.util.List;
import java.util.UUID;

public interface PatientService {
    PatientResponse createPatient(PatientCreateRequest request);
    PatientResponse getPatientById(UUID id);
    List<PatientResponse> getAllPatients(UUID clinicId);
    PatientResponse updatePatient(UUID id, PatientUpdateRequest request);
    void deletePatient(UUID id);
}
