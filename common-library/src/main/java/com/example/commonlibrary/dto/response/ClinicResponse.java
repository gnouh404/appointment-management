package com.example.commonlibrary.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ClinicResponse(
        String name,
        String address,
        String phone,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<UUID> doctorIds
) { }
