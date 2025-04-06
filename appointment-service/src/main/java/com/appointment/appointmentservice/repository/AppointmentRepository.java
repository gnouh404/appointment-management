package com.appointment.appointmentservice.repository;

import com.appointment.appointmentservice.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    List<Appointment> findByClinicId(UUID clinicId);
    List<Appointment> findByDoctorId(UUID doctorId);
}
