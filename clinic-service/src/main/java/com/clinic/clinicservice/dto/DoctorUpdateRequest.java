package com.clinic.clinicservice.dto;

import jakarta.validation.constraints.Pattern;

public record DoctorUpdateRequest(
        String fullName,
        String specialty,

        @Pattern(regexp = "\\d{10,15}", message = "Phone number must be 10-15 digits")
        String phone
) { }
