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
public class FileDto {
    private Long id;
    private Long ownerId;
    private Long projectId;
    private String name;
    private String content;
    private String extension;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
