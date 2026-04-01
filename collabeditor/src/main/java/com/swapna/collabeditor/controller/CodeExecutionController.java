package com.swapna.collabeditor.controller;

import com.swapna.collabeditor.dto.CodeExecutionDto;
import com.swapna.collabeditor.service.CodeExecutionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/code-executions")
@AllArgsConstructor
public class CodeExecutionController {

    private CodeExecutionService executionService;

    @PostMapping
    public ResponseEntity<CodeExecutionDto> addExecution(@RequestBody CodeExecutionDto dto) {
        CodeExecutionDto savedExecution = executionService.createExecution(dto);
        return new ResponseEntity<>(savedExecution, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<CodeExecutionDto> getExecutionById(@PathVariable("id") Long id) {
        CodeExecutionDto execution = executionService.getExecutionById(id);
        return ResponseEntity.ok(execution);
    }

    @GetMapping("/document/{documentId}")
    public ResponseEntity<List<CodeExecutionDto>> getExecutionsByDocument(@PathVariable("documentId") Long documentId) {
        List<CodeExecutionDto> executions = executionService.getExecutionsByDocument(documentId);
        return ResponseEntity.ok(executions);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CodeExecutionDto>> getExecutionsByUser(@PathVariable("userId") Long userId) {
        List<CodeExecutionDto> executions = executionService.getExecutionsByUser(userId);
        return ResponseEntity.ok(executions);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CodeExecutionDto> updateExecution(@PathVariable("id") Long id, @RequestBody CodeExecutionDto dto) {
        CodeExecutionDto updatedExecution = executionService.updateExecution(id, dto);
        return ResponseEntity.ok(updatedExecution);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExecution(@PathVariable("id") Long id) {
        executionService.deleteExecution(id);
        return ResponseEntity.ok("Code execution deleted successfully");
    }
}
