package com.notification.notificationservice.service.impl;

import com.notification.notificationservice.dto.NotificationRequest;
import com.notification.notificationservice.dto.NotificationResponse;
import com.notification.notificationservice.entity.Notification;
import com.notification.notificationservice.repository.NotificationRepository;
import com.notification.notificationservice.service.NotificationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository repo;

    @Override
    @Transactional
    public void saveNotification(NotificationRequest request) {
        Notification notification = Notification.builder()
                .userId(request.userId())
                .title(request.title())
                .content(request.content())
                .read(false)
                .createdAt(LocalDateTime.now())
                .build();
        repo.save(notification);
    }

    @Override
    public List<NotificationResponse> getNotifications(String userId) {
        return repo.findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(n ->
                        new NotificationResponse(n.getId(), n.getUserId(),
                                n.getTitle(), n.getContent(),
                                n.isRead(), n.getCreatedAt().toString()))
                .toList();
    }

    @Override
    @Transactional
    public void markAsRead(String id) {
        repo.findById(id).ifPresent(n -> {
            n.setRead(true);
            repo.save(n);
        });
    }

    @Override
    @Transactional
    public void delete(String id) {
        repo.deleteById(id);
    }


}
