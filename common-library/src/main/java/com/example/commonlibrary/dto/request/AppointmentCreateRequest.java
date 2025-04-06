package com.example.commonlibrary.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentCreateRequest(
        @NotNull UUID patientId,
        @NotNull UUID doctorId,
        @NotNull UUID clinicId,
        @NotNull LocalDateTime startTime,
        @NotNull LocalDateTime endTime,
        String reason,
        String status
) { }
