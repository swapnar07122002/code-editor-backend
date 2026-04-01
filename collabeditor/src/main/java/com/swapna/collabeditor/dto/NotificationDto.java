package com.swapna.collabeditor.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {
    private Long id;
    private Long userId;
    private String type;
    private String message;
    private Boolean isRead;
    private LocalDateTime createdAt;
}
