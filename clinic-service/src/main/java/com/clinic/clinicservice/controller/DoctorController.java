package com.clinic.clinicservice.controller;

import com.clinic.clinicservice.service.DoctorService;
import com.clinic.clinicservice.dto.DoctorCreateRequest;
import com.example.commonlibrary.dto.response.ApiResponse;
import com.example.commonlibrary.dto.response.clinic.DoctorResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<DoctorResponse>> create(@Valid @RequestBody DoctorCreateRequest request) {
            try {
                DoctorResponse response = doctorService.createDoctor(request);
                return ResponseEntity.ok(ApiResponse.success("Doctor created successfully", response));
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(ApiResponse.fail(e.getMessage()));
            }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DoctorResponse>> getDoctorById(@PathVariable UUID id) {
        try {
            DoctorResponse response = doctorService.getDoctorById(id);
            return ResponseEntity.ok(ApiResponse.success("Doctor fetched successfully", response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.fail(e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DoctorResponse>>> getAllDoctors() {
        try {
            List<DoctorResponse> response = doctorService.getAllDoctors();
            return ResponseEntity.ok(ApiResponse.success("All doctors fetched successfully", response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.fail(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DoctorResponse>> updateDoctor(@PathVariable UUID id, @Valid @RequestBody DoctorCreateRequest request) {
        try {
            DoctorResponse response = doctorService.updateDoctor(id, request);
            return ResponseEntity.ok(ApiResponse.success("Doctor updated successfully", response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.fail(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteDoctor(@PathVariable UUID id) {
        try {
            doctorService.deleteDoctor(id);
            return ResponseEntity.ok(ApiResponse.success("success", "Doctor deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.fail(e.getMessage()));
        }
    }
}
