package com.swapna.collabeditor.mapper;

import com.swapna.collabeditor.dto.DocumentVersionDto;
import com.swapna.collabeditor.entity.File;
import com.swapna.collabeditor.entity.DocumentVersion;
import com.swapna.collabeditor.entity.User;

public class DocumentVersionMapper {

    // convert Document version entity to dto
    public static DocumentVersionDto mapToDocumentVersionDto(DocumentVersion documentVersion) {
        return new DocumentVersionDto(
                documentVersion.getId(),
                documentVersion.getDocument().getId(),
                documentVersion.getEditor() != null ? documentVersion.getEditor().getId() : null,
                documentVersion.getVersionNumber(),
                documentVersion.getContentSnapshot(),
                documentVersion.getChangeSummary(),
                documentVersion.getCreatedAt()
        );
    }

    // convert document version dto to entity
    public static DocumentVersion mapToDocumentVersion(DocumentVersionDto documentVersionDto, File document, User editor) {
        return new DocumentVersion(
                documentVersionDto.getId(),
                document,
                editor,
                documentVersionDto.getVersionNumber(),
                documentVersionDto.getContentSnapshot(),
                documentVersionDto.getChangeSummary(),
                documentVersionDto.getCreatedAt()
        );
    }
}
