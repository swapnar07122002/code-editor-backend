package com.swapna.collabeditor.service.impl;

import com.swapna.collabeditor.dto.CodeExecutionDto;
import com.swapna.collabeditor.entity.CodeExecution;
import com.swapna.collabeditor.entity.File;
import com.swapna.collabeditor.entity.User;
import com.swapna.collabeditor.exception.ResourceNotFoundException;
import com.swapna.collabeditor.mapper.CodeExecutionMapper;
import com.swapna.collabeditor.repository.CodeExecutionRepository;
import com.swapna.collabeditor.repository.FileRepository;
import com.swapna.collabeditor.repository.UserRepository;
import com.swapna.collabeditor.service.CodeExecutionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CodeExecutionServiceImpl implements CodeExecutionService {

    private CodeExecutionRepository codeExecutionRepository;
    private FileRepository documentRepository;
    private UserRepository userRepository;


    @Override
    public CodeExecutionDto createExecution(CodeExecutionDto codeExecutionDto) {

        // Fetch Document id from DB
        File document = documentRepository.findById(codeExecutionDto.getDocumentId())
                .orElseThrow(() -> new ResourceNotFoundException("document not found with id: "+codeExecutionDto.getDocumentId()));

        // Fetch User entity from DB
        User user = userRepository.findById(codeExecutionDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("user not found with id: "+codeExecutionDto.getUserId()));

        // convert dto to entity
        CodeExecution codeExecution = CodeExecutionMapper.mapToCodeExecution(codeExecutionDto, document, user);

        // save entity into DB
        CodeExecution savedCodeExecution = codeExecutionRepository.save(codeExecution);

        // convert entity to dto
        return CodeExecutionMapper.mapToCodeExecutionDto(savedCodeExecution);
    }

    @Override
    public CodeExecutionDto getExecutionById(Long codeExecutionId) {

        CodeExecution codeExecution = codeExecutionRepository.findById(codeExecutionId)
                .orElseThrow(() -> new ResourceNotFoundException("Code execution not found with id: " + codeExecutionId));

        return CodeExecutionMapper.mapToCodeExecutionDto(codeExecution);
    }

    @Override
    public List<CodeExecutionDto> getExecutionsByDocument(Long documentId) {

        List<CodeExecution> codeExecutions = codeExecutionRepository.findByDocumentId(documentId);
        return codeExecutions.stream().map((codeExecution) -> CodeExecutionMapper.mapToCodeExecutionDto(codeExecution))
                .collect(Collectors.toList());
    }

    @Override
    public List<CodeExecutionDto> getExecutionsByUser(Long userId) {

        List<CodeExecution> codeExecutions = codeExecutionRepository.findByUserId(userId);
        return codeExecutions.stream().map((codeExecution) -> CodeExecutionMapper.mapToCodeExecutionDto(codeExecution))
                .collect(Collectors.toList());
    }

    @Override
    public CodeExecutionDto updateExecution(Long codeExecutionId, CodeExecutionDto updatedCodeExecutionDto) {

        CodeExecution codeExecution = codeExecutionRepository.findById(codeExecutionId)
                .orElseThrow(() -> new ResourceNotFoundException("Code execution not found with id: " + codeExecutionId));

        codeExecution.setInput(updatedCodeExecutionDto.getInput());
        codeExecution.setOutput(updatedCodeExecutionDto.getOutput());
        codeExecution.setErrors(updatedCodeExecutionDto.getErrors());
        codeExecution.setStatus(updatedCodeExecutionDto.getStatus());

        CodeExecution updatedCodeExecution = codeExecutionRepository.save(codeExecution);
        return CodeExecutionMapper.mapToCodeExecutionDto(updatedCodeExecution);
    }

    @Override
    public void deleteExecution(Long codeExecutionId) {

        CodeExecution codeExecution = codeExecutionRepository.findById(codeExecutionId)
                .orElseThrow(() -> new ResourceNotFoundException("Code execution not found with id: " + codeExecutionId));

        codeExecutionRepository.delete(codeExecution);
    }
}
