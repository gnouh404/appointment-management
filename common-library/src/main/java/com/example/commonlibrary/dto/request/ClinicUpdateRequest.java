package com.example.commonlibrary.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ClinicUpdateRequest(
        String name,
        String address,

        @Pattern(regexp = "\\d{10,15}", message = "Phone number must be 10-15 digits")
        String phone
) { }
