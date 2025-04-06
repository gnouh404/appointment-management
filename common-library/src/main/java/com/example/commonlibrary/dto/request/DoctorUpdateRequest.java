package com.example.commonlibrary.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.List;
import java.util.UUID;

public record DoctorUpdateRequest(
        String fullName,
        String specialty,

        @Pattern(regexp = "\\d{10,15}", message = "Phone number must be 10-15 digits")
        String phone
) { }
