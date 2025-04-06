package com.example.commonlibrary.dto.request;

import lombok.NonNull;

import java.util.UUID;

public record DoctorClinicRequest(
        @NonNull
        UUID doctorId,
        @NonNull
        UUID clinicId
) { }
