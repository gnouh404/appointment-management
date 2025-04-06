package com.example.commonlibrary.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentResponse (
    UUID id,
    UUID patientId,
    UUID doctorId,
    UUID clinicId,
    LocalDateTime startTime,
    LocalDateTime endTime,
    String reason,
    String status
) {}
