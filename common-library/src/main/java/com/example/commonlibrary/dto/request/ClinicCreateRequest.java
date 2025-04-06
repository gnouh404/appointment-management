package com.example.commonlibrary.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ClinicCreateRequest(
        @NotBlank(message = "Clinic name is required")
        String name,
        @NotBlank(message = "Address is required")
        String address,
        @Pattern(regexp = "\\d{10,15}", message = "Phone number must be 10-15 digits")
        String phone
) { }
