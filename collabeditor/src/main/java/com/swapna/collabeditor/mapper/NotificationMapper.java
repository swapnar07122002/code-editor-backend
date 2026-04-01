package com.swapna.collabeditor.mapper;

import com.swapna.collabeditor.dto.NotificationDto;
import com.swapna.collabeditor.entity.Notification;
import com.swapna.collabeditor.entity.User;

public class NotificationMapper {

    public static NotificationDto mapToNotificationDto(Notification notification) {
        return new NotificationDto(
                notification.getId(),
                notification.getUser().getId(),
                notification.getType(),
                notification.getMessage(),
                notification.getIsRead(),
                notification.getCreatedAt()
        );
    }

    public static Notification mapToNotification(NotificationDto notificationDto, User user) {
        return new Notification(
                notificationDto.getId(),
                user,
                notificationDto.getType(),
                notificationDto.getMessage(),
                notificationDto.getIsRead(),
                notificationDto.getCreatedAt()
        );
    }
}
