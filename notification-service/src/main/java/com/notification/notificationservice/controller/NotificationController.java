package com.notification.notificationservice.controller;

import com.example.commonlibrary.dto.response.ApiResponse;
import com.notification.notificationservice.dto.NotificationRequest;
import com.notification.notificationservice.dto.NotificationResponse;
import com.notification.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> saveNotification(@RequestBody NotificationRequest request) {
        try {
            notificationService.saveNotification(request);
            return ResponseEntity.ok(ApiResponse.success("Notification saved", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.fail(e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> sendNotification(@PathVariable String id) {
        try {
            return ResponseEntity.ok(ApiResponse.success("Notifications fetched", notificationService.getNotifications(id)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.fail(e.getMessage()));
        }
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<ApiResponse<Void>> markAsRead(@PathVariable String id) {
        try {
            notificationService.markAsRead(id);
            return ResponseEntity.ok(ApiResponse.success("Notification marked as read", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.fail(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteNotification(@PathVariable String id) {
        try {
            notificationService.delete(id);
            return ResponseEntity.ok(ApiResponse.success("Notification deleted", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.fail(e.getMessage()));
        }
    }
}
