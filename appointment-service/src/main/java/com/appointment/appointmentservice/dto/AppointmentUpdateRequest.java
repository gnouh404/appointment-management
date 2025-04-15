package com.appointment.appointmentservice.dto;

import java.time.LocalDateTime;

public record AppointmentUpdateRequest(
        LocalDateTime startTime,
        LocalDateTime endTime,
        String reason,
        String status
) { }
