package com.clinic.clinicservice.dto;

import jakarta.validation.constraints.Pattern;

public record ClinicUpdateRequest(
        String name,
        String address,

        @Pattern(regexp = "\\d{10,15}", message = "Phone number must be 10-15 digits")
        String phone
) { }
