package com.swapna.collabeditor.mapper;

import com.swapna.collabeditor.dto.ExecutionSessionDto;
import com.swapna.collabeditor.entity.File;
import com.swapna.collabeditor.entity.ExecutionSession;
import com.swapna.collabeditor.entity.User;

public class ExecutionSessionMapper {

    // convert entity to dto
    public static ExecutionSessionDto mapToExecutionSessionDto(ExecutionSession session) {
        return new ExecutionSessionDto(
                session.getId(),
                session.getDocument().getId(),
                session.getUser().getId(),
                session.getSessionStatus(),
                session.getStartedAt(),
                session.getFinishedAt()
        );
    }

    // convert dto to entity
    public static ExecutionSession mapToExecutionSession(ExecutionSessionDto dto, File document, User user) {
        return new ExecutionSession(
                dto.getId(),
                document,
                user,
                dto.getSessionStatus(),
                dto.getStartedAt() != null ? dto.getStartedAt() : java.time.LocalDateTime.now(),
                dto.getFinishedAt()
        );
    }
}
