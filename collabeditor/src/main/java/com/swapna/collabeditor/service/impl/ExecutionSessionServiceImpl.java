package com.swapna.collabeditor.service.impl;

import com.swapna.collabeditor.dto.ExecutionSessionDto;
import com.swapna.collabeditor.entity.File;
import com.swapna.collabeditor.entity.ExecutionSession;
import com.swapna.collabeditor.entity.User;
import com.swapna.collabeditor.exception.ResourceNotFoundException;
import com.swapna.collabeditor.mapper.ExecutionSessionMapper;
import com.swapna.collabeditor.repository.FileRepository;
import com.swapna.collabeditor.repository.ExecutionSessionRepository;
import com.swapna.collabeditor.repository.UserRepository;
import com.swapna.collabeditor.service.ExecutionSessionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ExecutionSessionServiceImpl implements ExecutionSessionService {

    private ExecutionSessionRepository sessionRepository;
    private FileRepository documentRepository;
    private UserRepository userRepository;

    @Override
    public ExecutionSessionDto createSession(ExecutionSessionDto sessionDto) {

        // Fetch Document
        File document = documentRepository.findById(sessionDto.getDocumentId())
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with id: " + sessionDto.getDocumentId()));

        // Fetch User
        User user = userRepository.findById(sessionDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + sessionDto.getUserId()));

        // Convert DTO to Entity
        ExecutionSession session = ExecutionSessionMapper.mapToExecutionSession(sessionDto, document, user);

        // Save Entity
        ExecutionSession savedSession = sessionRepository.save(session);

        // Convert Entity to DTO
        return ExecutionSessionMapper.mapToExecutionSessionDto(savedSession);
    }

    @Override
    public ExecutionSessionDto getSessionById(Long sessionId) {
        ExecutionSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Execution session not found with id: " + sessionId));

        return ExecutionSessionMapper.mapToExecutionSessionDto(session);
    }

    @Override
    public List<ExecutionSessionDto> getSessionsByDocument(Long documentId) {
        List<ExecutionSession> sessions = sessionRepository.findByDocumentId(documentId);
        return sessions.stream()
                .map(ExecutionSessionMapper::mapToExecutionSessionDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ExecutionSessionDto> getSessionsByUser(Long userId) {
        List<ExecutionSession> sessions = sessionRepository.findByUserId(userId);
        return sessions.stream()
                .map(ExecutionSessionMapper::mapToExecutionSessionDto)
                .collect(Collectors.toList());
    }

    @Override
    public ExecutionSessionDto updateSession(Long sessionId, ExecutionSessionDto updatedSessionDto) {
        ExecutionSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Execution session not found with id: " + sessionId));

        session.setSessionStatus(updatedSessionDto.getSessionStatus());
        session.setStartedAt(updatedSessionDto.getStartedAt());
        session.setFinishedAt(updatedSessionDto.getFinishedAt());

        ExecutionSession updatedSession = sessionRepository.save(session);
        return ExecutionSessionMapper.mapToExecutionSessionDto(updatedSession);
    }

    @Override
    public void deleteSession(Long sessionId) {
        ExecutionSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Execution session not found with id: " + sessionId));

        sessionRepository.delete(session);
    }
}
