package com.notification.notificationservice.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notification.notificationservice.dto.NotificationRequest;
import com.notification.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AppointmentEventListener {

    private final NotificationService notificationService;
    private final ObjectMapper mapper;

    public void listen(String messageJson) {
        try {
            NotificationRequest request = mapper.readValue(messageJson, NotificationRequest.class);
            notificationService.saveNotification(request);
        } catch (Exception e) {
            log.error("Error saving notification", e);
        }
    }
}
