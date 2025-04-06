package com.clinic.clinicservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "doctor_clinics", schema = "clinic")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorClinic {

    @EmbeddedId
    private DoctorClinicId id;

    @ManyToOne
    @MapsId("doctorId")
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne
    @MapsId("clinicId")
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;
}

