package com.swapna.collabeditor.controller;

import com.swapna.collabeditor.dto.ExecutionSessionDto;
import com.swapna.collabeditor.service.ExecutionSessionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/execution-sessions")
@AllArgsConstructor
public class ExecutionSessionController {

    private ExecutionSessionService sessionService;

    @PostMapping
    public ResponseEntity<ExecutionSessionDto> addSession(@RequestBody ExecutionSessionDto dto) {
        ExecutionSessionDto savedSession = sessionService.createSession(dto);
        return new ResponseEntity<>(savedSession, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExecutionSessionDto> getSessionById(@PathVariable("id") Long id) {
        ExecutionSessionDto session = sessionService.getSessionById(id);
        return ResponseEntity.ok(session);
    }

    @GetMapping("/document/{documentId}")
    public ResponseEntity<List<ExecutionSessionDto>> getSessionsByDocument(@PathVariable("documentId") Long documentId) {
        List<ExecutionSessionDto> sessions = sessionService.getSessionsByDocument(documentId);
        return ResponseEntity.ok(sessions);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ExecutionSessionDto>> getSessionsByUser(@PathVariable("userId") Long userId) {
        List<ExecutionSessionDto> sessions = sessionService.getSessionsByUser(userId);
        return ResponseEntity.ok(sessions);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExecutionSessionDto> updateSession(@PathVariable("id") Long id, @RequestBody ExecutionSessionDto dto) {
        ExecutionSessionDto updatedSession = sessionService.updateSession(id, dto);
        return ResponseEntity.ok(updatedSession);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSession(@PathVariable("id") Long id) {
        sessionService.deleteSession(id);
        return ResponseEntity.ok("Execution session deleted successfully");
    }
}
