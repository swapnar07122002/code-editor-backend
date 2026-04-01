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
public class CodeExecutionDto {

    private Long id;
    private Long documentId;
    private Long userId;
    private String input;
    private String output;
    private String errors;
    private String status;
    private LocalDateTime executedAt;
}
