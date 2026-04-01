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
public class ExecutionSessionDto {

    private Long id;
    private Long documentId;
    private Long userId;
    private String sessionStatus; // pending, running, completed, failed
    private LocalDateTime startedAt;
    private LocalDateTime finishedAt;
}
