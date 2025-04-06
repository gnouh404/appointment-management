package com.clinic.clinicservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorClinicId implements Serializable {
    @Column(name = "doctor_id")
    private UUID doctorId;

    @Column(name = "clinic_id")
    private UUID clinicId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DoctorClinicId other = (DoctorClinicId) o;
        return Objects.equals(this.doctorId, other.doctorId) && Objects.equals(this.clinicId, other.clinicId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(doctorId, clinicId);
    }
}
