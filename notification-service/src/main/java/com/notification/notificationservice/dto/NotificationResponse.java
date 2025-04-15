package com.notification.notificationservice.dto;

public record NotificationResponse(
        String id,
        String userId,
        String title,
        String content,
        boolean read,
        String createdAt
) { }
