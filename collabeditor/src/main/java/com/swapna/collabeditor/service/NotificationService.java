package com.swapna.collabeditor.service;

import com.swapna.collabeditor.dto.NotificationDto;

import java.util.List;

public interface NotificationService {

    NotificationDto addNotification(NotificationDto notificationDto);

    NotificationDto getNotificationById(Long notificationId);

    List<NotificationDto> getNotificationsByUser(Long userId);

    NotificationDto markAsRead(Long notificationId);

    void deleteNotification(Long notificationId);
}
