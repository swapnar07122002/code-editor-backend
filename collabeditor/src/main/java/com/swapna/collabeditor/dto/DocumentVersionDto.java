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
public class DocumentVersionDto {
    private Long id;
    private Long documentId;
    private Long editorId;
    private Integer versionNumber;
    private String contentSnapshot;
    private String changeSummary;
    private LocalDateTime createdAt;
}
