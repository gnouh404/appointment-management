package com.clinic.clinicservice.service;

import com.example.commonlibrary.dto.request.DoctorClinicRequest;

import java.util.UUID;

public interface DoctorClinicService {
    void assignDoctorToClinic(DoctorClinicRequest doctorClinicRequest);
    void removeDoctorFromClinic(DoctorClinicRequest doctorClinicRequest);
    boolean existsByDoctorAndClinic(UUID doctorId, UUID clinicId);
}
