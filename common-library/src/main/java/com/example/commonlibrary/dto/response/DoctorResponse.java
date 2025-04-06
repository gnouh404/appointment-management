package com.example.commonlibrary.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record DoctorResponse(
    String fullName,
    String specialty,
    String phone,
    String email,
    LocalDateTime updatedAt,
    LocalDateTime createdAt,
    List<UUID> clinicIds
) { }
