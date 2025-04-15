package com.clinic.clinicservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.List;
import java.util.UUID;

public record DoctorCreateRequest(
        @NotBlank(message = "Full name is required")
        String fullName,

        String specialty,

        @Pattern(regexp = "\\d{10,15}", message = "Phone number must be 10-15 digits")
        String phone,
        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,

        List<UUID> clinicIds // Optional: để gán luôn clinics khi tạo
) { }
