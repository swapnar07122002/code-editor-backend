package com.swapna.collabeditor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminLogDto {
    private Long id;
    private Long adminId;
    private String action;
    private Long targetId;
    private String targetType;
    private LocalDateTime timestamp;
}
