package com.patient.patientservice.service;

import com.patient.patientservice.dto.PatientCreateRequest;
import com.patient.patientservice.dto.PatientUpdateRequest;
import com.example.commonlibrary.dto.response.patient.PatientResponse;

import java.util.List;
import java.util.UUID;

public interface PatientService {
    PatientResponse createPatient(PatientCreateRequest request);
    PatientResponse getPatientById(UUID id);
    List<PatientResponse> getAllPatients(UUID clinicId);
    PatientResponse updatePatient(UUID id, PatientUpdateRequest request);
    void deletePatient(UUID id);
}
