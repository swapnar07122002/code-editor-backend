package com.swapna.collabeditor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EditorMessageDto {
    private String roomId;
    private String userId;
    private String type;    // e.g. "FULL", "PATCH", "CURSOR"
    private String content; // document text or patch or cursor info
}
