package com.patient.patientservice.dto;

import jakarta.validation.constraints.Pattern;

import java.util.UUID;

public record PatientUpdateRequest(
        String email,

        @Pattern(regexp = "\\d{10,15}", message = "Phone number must be 10-15 digits")
        String phone,
        UUID clinicId
) {}
