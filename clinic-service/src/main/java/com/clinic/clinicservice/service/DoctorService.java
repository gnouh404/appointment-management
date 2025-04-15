package com.clinic.clinicservice.service;

import com.clinic.clinicservice.dto.DoctorCreateRequest;
import com.example.commonlibrary.dto.response.clinic.DoctorResponse;

import java.util.List;
import java.util.UUID;

public interface DoctorService {
    DoctorResponse createDoctor(DoctorCreateRequest doctorCreateRequest);
    DoctorResponse getDoctorById(UUID id);
    List<DoctorResponse> getAllDoctors();
    DoctorResponse updateDoctor(UUID id, DoctorCreateRequest doctorCreateRequest);
    void deleteDoctor(UUID id);
}
