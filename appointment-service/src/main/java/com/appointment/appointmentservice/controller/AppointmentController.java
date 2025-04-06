package com.appointment.appointmentservice.controller;

import com.appointment.appointmentservice.service.AppointmentService;
import com.example.commonlibrary.dto.request.AppointmentCreateRequest;
import com.example.commonlibrary.dto.request.AppointmentUpdateRequest;
import com.example.commonlibrary.dto.response.ApiResponse;
import com.example.commonlibrary.dto.response.AppointmentResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService service;

    @PostMapping
    public ResponseEntity<ApiResponse<AppointmentResponse>> create(@RequestBody @Valid AppointmentCreateRequest request) {
        try {
            return ResponseEntity.ok(ApiResponse.success("Appointment created", service.create(request)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.fail(e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AppointmentResponse>> getById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(ApiResponse.success("Appointment fetched", service.getById(id)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.fail(e.getMessage()));
        }
    }

    @GetMapping("/by-clinic")
    public ResponseEntity<ApiResponse<List<AppointmentResponse>>> getByClinic(@RequestParam UUID clinicId) {
        try {
            return ResponseEntity.ok(ApiResponse.success("Appointments fetched", service.getByClinicId(clinicId)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.fail(e.getMessage()));
        }
    }

    @GetMapping("/by-doctor")
    public ResponseEntity<ApiResponse<List<AppointmentResponse>>> getByDoctor(@RequestParam UUID doctorId) {
        try {
            return ResponseEntity.ok(ApiResponse.success("Appointments fetched", service.getByDoctorId(doctorId)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.fail(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AppointmentResponse>> update(@PathVariable UUID id, @RequestBody AppointmentUpdateRequest request) {
        try {
            return ResponseEntity.ok(ApiResponse.success("Appointment updated", service.update(id, request)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.fail(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
        try {
            service.delete(id);
            return ResponseEntity.ok(ApiResponse.success("Appointment deleted", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.fail(e.getMessage()));
        }
    }
}

