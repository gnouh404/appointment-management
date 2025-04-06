package com.patient.patientservice.controller;

import com.example.commonlibrary.dto.request.PatientCreateRequest;
import com.example.commonlibrary.dto.request.PatientUpdateRequest;
import com.example.commonlibrary.dto.response.ApiResponse;
import com.example.commonlibrary.dto.response.PatientResponse;
import com.patient.patientservice.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<PatientResponse>> create(@Valid @RequestBody PatientCreateRequest request){
        try {
            PatientResponse response = patientService.createPatient(request);
            return ResponseEntity.ok(ApiResponse.success("Patient created successfully", response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.fail(e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PatientResponse>> getPatientById(@PathVariable UUID id){
        try {
            PatientResponse response = patientService.getPatientById(id);
            return ResponseEntity.ok(ApiResponse.success("Patient found successfully", response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.fail(e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PatientResponse>>> getAllPatients(@RequestParam(required = false) UUID clinicId){
        try {
            List<PatientResponse> response = patientService.getAllPatients(clinicId);
            return ResponseEntity.ok(ApiResponse.success("Patient found successfully", response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.fail(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PatientResponse>> updatePatient(@PathVariable UUID id, @Valid @RequestBody PatientUpdateRequest request){
        try {
            PatientResponse response = patientService.updatePatient(id, request);
            return ResponseEntity.ok(ApiResponse.success("Patient updated successfully", response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.fail(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deletePatient(@PathVariable UUID id){
        try {
            patientService.deletePatient(id);
            return ResponseEntity.ok(ApiResponse.success("success", "Patient deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.fail(e.getMessage()));
        }
    }
}
