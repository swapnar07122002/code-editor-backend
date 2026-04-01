package com.swapna.collabeditor.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Long id;
    private Long documentId;
    private Long userId;
    private String content;
    private Integer lineNumber;
    private LocalDateTime createdAt;
}
