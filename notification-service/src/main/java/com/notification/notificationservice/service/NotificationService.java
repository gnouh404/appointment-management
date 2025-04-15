package com.notification.notificationservice.service;

import com.notification.notificationservice.dto.NotificationRequest;
import com.notification.notificationservice.dto.NotificationResponse;

import java.util.List;
import java.util.UUID;

public interface NotificationService {
    void saveNotification(NotificationRequest request);
    List<NotificationResponse> getNotifications(String userId);
    void markAsRead(String id);
    void delete(String id);
}
