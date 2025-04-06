package com.patient.patientservice.repository;

import com.patient.patientservice.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PatientRepository extends JpaRepository<Patient, UUID> {
    List<Patient> findPatientByClinicId(UUID clinicId);
}
