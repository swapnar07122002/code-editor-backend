package com.swapna.collabeditor.service;

import com.swapna.collabeditor.dto.ExecutionSessionDto;

import java.util.List;

public interface ExecutionSessionService {

    ExecutionSessionDto createSession(ExecutionSessionDto sessionDto);

    ExecutionSessionDto getSessionById(Long sessionId);

    List<ExecutionSessionDto> getSessionsByDocument(Long documentId);

    List<ExecutionSessionDto> getSessionsByUser(Long userId);

    ExecutionSessionDto updateSession(Long sessionId, ExecutionSessionDto dto);

    void deleteSession(Long sessionId);
}
