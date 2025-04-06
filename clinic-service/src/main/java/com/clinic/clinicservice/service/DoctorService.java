package com.clinic.clinicservice.service;

import com.example.commonlibrary.dto.request.DoctorCreateRequest;
import com.example.commonlibrary.dto.response.DoctorResponse;

import java.util.List;
import java.util.UUID;

public interface DoctorService {
    DoctorResponse createDoctor(DoctorCreateRequest doctorCreateRequest);
    DoctorResponse getDoctorById(UUID id);
    List<DoctorResponse> getAllDoctors();
    DoctorResponse updateDoctor(UUID id, DoctorCreateRequest doctorCreateRequest);
    void deleteDoctor(UUID id);
}
