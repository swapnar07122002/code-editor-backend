package com.swapna.collabeditor.service.impl;

import com.swapna.collabeditor.dto.NotificationDto;
import com.swapna.collabeditor.entity.Notification;
import com.swapna.collabeditor.entity.User;
import com.swapna.collabeditor.exception.ResourceNotFoundException;
import com.swapna.collabeditor.mapper.NotificationMapper;
import com.swapna.collabeditor.repository.NotificationRepository;
import com.swapna.collabeditor.repository.UserRepository;
import com.swapna.collabeditor.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private NotificationRepository notificationRepository;
    private UserRepository userRepository;

    @Override
    public NotificationDto addNotification(NotificationDto notificationDto) {

        // fetch user id from DB
        User user = userRepository.findById(notificationDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + notificationDto.getUserId()));

        // convert dto to entity
        Notification notification = NotificationMapper.mapToNotification(notificationDto, user);

        // save into DB
        Notification saved = notificationRepository.save(notification);

        // convert entity to dto
        return NotificationMapper.mapToNotificationDto(saved);
    }

    @Override
    public NotificationDto getNotificationById(Long notificationId) {

        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + notificationId));

        return NotificationMapper.mapToNotificationDto(notification);
    }

    @Override
    public List<NotificationDto> getNotificationsByUser(Long userId) {

        List<Notification> notifications = notificationRepository.findByUserId(userId);

        return notifications.stream().map((notification) -> NotificationMapper.mapToNotificationDto(notification))
                .collect(Collectors.toList());
    }

    @Override
    public NotificationDto markAsRead(Long notificationId) {

        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + notificationId));

        notification.setIsRead(true);

        return NotificationMapper.mapToNotificationDto(notificationRepository.save(notification));
    }

    @Override
    public void deleteNotification(Long notificationId) {

        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + notificationId));

        notificationRepository.delete(notification);
    }
}
