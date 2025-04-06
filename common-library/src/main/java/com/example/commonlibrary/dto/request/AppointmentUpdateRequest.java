package com.example.commonlibrary.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentUpdateRequest(
        LocalDateTime startTime,
        LocalDateTime endTime,
        String reason,
        String status
) { }
