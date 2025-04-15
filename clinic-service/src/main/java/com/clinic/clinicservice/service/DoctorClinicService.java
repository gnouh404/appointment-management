package com.clinic.clinicservice.service;

import com.clinic.clinicservice.dto.DoctorClinicRequest;

import java.util.UUID;

public interface DoctorClinicService {
    void assignDoctorToClinic(DoctorClinicRequest doctorClinicRequest);
    void removeDoctorFromClinic(DoctorClinicRequest doctorClinicRequest);
    boolean existsByDoctorAndClinic(UUID doctorId, UUID clinicId);
}
