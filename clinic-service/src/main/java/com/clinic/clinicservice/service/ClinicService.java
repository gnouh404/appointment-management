package com.clinic.clinicservice.service;

import com.clinic.clinicservice.dto.ClinicCreateRequest;
import com.clinic.clinicservice.dto.ClinicUpdateRequest;
import com.example.commonlibrary.dto.response.clinic.ClinicResponse;

import java.util.List;
import java.util.UUID;

public interface ClinicService {
    ClinicResponse createClinic(ClinicCreateRequest clinicCreateRequest);
    ClinicResponse getClinic(UUID id);
    List<ClinicResponse> getAllClinics();
    ClinicResponse updateClinic(UUID id, ClinicUpdateRequest request);
    void deleteClinic(UUID id);
}
