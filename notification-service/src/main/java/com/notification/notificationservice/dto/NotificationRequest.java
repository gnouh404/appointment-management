package com.notification.notificationservice.dto;

import java.util.UUID;

public record NotificationRequest(String userId, String title, String content) {
}
