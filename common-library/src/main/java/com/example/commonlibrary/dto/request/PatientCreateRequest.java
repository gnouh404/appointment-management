package com.example.commonlibrary.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;
import java.util.UUID;

public record PatientCreateRequest(
        @NotBlank(message = "Full name is required")
        String fullName,
        @NotBlank(message = "Email is required")
        String email,
        @Pattern(regexp = "\\d{10,15}", message = "Phone number must be 10-15 digits")
        String phone,
        @NotNull(message = "Date of birth is required")
        LocalDateTime dob,
        @NotBlank(message = "Gender is required")
        String gender,
        @NotNull(message = "Clinic ID is required")
        UUID clinicId
) {}
