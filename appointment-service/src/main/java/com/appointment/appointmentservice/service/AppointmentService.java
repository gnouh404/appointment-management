package com.appointment.appointmentservice.service;

import com.example.commonlibrary.dto.request.AppointmentCreateRequest;
import com.example.commonlibrary.dto.request.AppointmentUpdateRequest;
import com.example.commonlibrary.dto.response.AppointmentResponse;

import java.util.List;
import java.util.UUID;

public interface AppointmentService {
    AppointmentResponse create(AppointmentCreateRequest request);
    AppointmentResponse getById(UUID id);
    List<AppointmentResponse> getByClinicId(UUID clinicId);
    List<AppointmentResponse> getByDoctorId(UUID doctorId);
    AppointmentResponse update(UUID id, AppointmentUpdateRequest request);
    void delete(UUID id);
}
