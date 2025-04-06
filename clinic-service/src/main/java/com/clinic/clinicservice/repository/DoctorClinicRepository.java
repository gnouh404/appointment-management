package com.clinic.clinicservice.repository;

import com.clinic.clinicservice.entity.DoctorClinic;
import com.clinic.clinicservice.entity.DoctorClinicId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DoctorClinicRepository extends JpaRepository<DoctorClinic, DoctorClinicId> {
    List<DoctorClinic> findAllByDoctorId(UUID doctorId);
    List<DoctorClinic> findAllByClinicId(UUID clinicId);
    boolean existsByDoctorIdAndClinicId(UUID doctorId, UUID clinicId);
}
