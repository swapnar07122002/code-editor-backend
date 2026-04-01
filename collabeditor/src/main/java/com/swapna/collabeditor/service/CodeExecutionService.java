package com.swapna.collabeditor.service;

import com.swapna.collabeditor.dto.CodeExecutionDto;

import java.util.List;

public interface CodeExecutionService {

    CodeExecutionDto createExecution(CodeExecutionDto codeExecutionDto);

    CodeExecutionDto getExecutionById(Long codeExecutionId);

    List<CodeExecutionDto> getExecutionsByDocument(Long documentId);

    List<CodeExecutionDto> getExecutionsByUser(Long userId);

    CodeExecutionDto updateExecution(Long codeExecutionId, CodeExecutionDto updatedCodeExecutionDto);

    void deleteExecution(Long codeExecutionId);

    interface ProjectService {
    }
}
