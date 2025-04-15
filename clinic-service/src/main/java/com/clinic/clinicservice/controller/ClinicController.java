package com.clinic.clinicservice.controller;

import com.clinic.clinicservice.service.ClinicService;
import com.clinic.clinicservice.dto.ClinicCreateRequest;
import com.clinic.clinicservice.dto.ClinicUpdateRequest;
import com.example.commonlibrary.dto.response.ApiResponse;
import com.example.commonlibrary.dto.response.clinic.ClinicResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/api/clinics")
@RequiredArgsConstructor
public class ClinicController {

    private final ClinicService clinicService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<ClinicResponse>> create(@Valid @RequestBody ClinicCreateRequest request){
        try {
            ClinicResponse response = clinicService.createClinic(request);
            return ResponseEntity.ok(ApiResponse.success("Clinic created successfully", response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.fail(e.getMessage()));
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ClinicResponse>> getClinicById(@PathVariable UUID id){
        try {
            ClinicResponse response = clinicService.getClinic(id);
            return ResponseEntity.ok(ApiResponse.success("Clinic fetched successfully", response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.fail(e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ClinicResponse>>> getAllClinics(){
        try {
            List<ClinicResponse> response = clinicService.getAllClinics();
            return ResponseEntity.ok(ApiResponse.success("All clinics fetched successfully", response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.fail(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ClinicResponse>> updateClinic(@PathVariable UUID id,
                                                                    @Valid @RequestBody ClinicUpdateRequest request){
        try {
            ClinicResponse response = clinicService.updateClinic(id, request);
            return ResponseEntity.ok(ApiResponse.success("Clinic updated successfully", response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.fail(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteClinic(@PathVariable UUID id){
        try {
            clinicService.deleteClinic(id);
            return ResponseEntity.ok(ApiResponse.success("success", "Clinic deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.fail(e.getMessage()));
        }
    }


}
