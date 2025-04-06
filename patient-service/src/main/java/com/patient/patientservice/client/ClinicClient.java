package com.patient.patientservice.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ClinicClient {

    private final RestTemplate restTemplate;

    public void validateClinicExists(UUID id) {
        String url = UriComponentsBuilder.fromUriString("http://localhost:8082/api/clinics/{id}")
                .buildAndExpand(id).toUriString();
        try {
            restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            throw new RuntimeException("Invalid clinic id: " + id);
        }
    }
}
