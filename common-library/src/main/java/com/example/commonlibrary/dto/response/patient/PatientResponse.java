package com.example.commonlibrary.dto.response.patient;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record PatientResponse(
    UUID id,
    String fullName,
    String email,
    String phone,
    LocalDateTime dob,
    String gender,
    UUID clinicId,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}
