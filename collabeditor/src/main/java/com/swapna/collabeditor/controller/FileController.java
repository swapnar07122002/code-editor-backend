package com.swapna.collabeditor.controller;

import com.swapna.collabeditor.dto.FileDto;
import com.swapna.collabeditor.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("api/files")
public class FileController {

    private FileService fileService;

    // Build Add File REST API
    @PostMapping
    public ResponseEntity<?> createFile(@RequestBody FileDto fileDto) {
        FileDto savedFile = fileService.createFile(fileDto, fileDto.getOwnerId(), fileDto.getProjectId());
        return ResponseEntity.ok(Map.of(
                "message", "File created successfully",
                "file", savedFile
        ));
    }

    // Build Get File REST API
    @GetMapping("{id}")
    public ResponseEntity<FileDto> getFileById(@PathVariable("id") Long fileId) {
        FileDto fileDto = fileService.getFileById(fileId);
        return ResponseEntity.ok(fileDto);
    }

    // Build Get All Files by Owner REST API
    @GetMapping("/user/{ownerId}")
    public ResponseEntity<List<FileDto>> getAllFilesByOwner(@PathVariable("ownerId") Long ownerId) {
        List<FileDto> files = fileService.getAllFilesByOwner(ownerId);
        return ResponseEntity.ok(files);
    }

    // Build Get All Files by Project REST API
    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<FileDto>> getAllFilesByProject(@PathVariable("projectId") Long projectId) {
        List<FileDto> files = fileService.getAllFilesByProject(projectId);
        return ResponseEntity.ok(files);
    }

    // Get file by Project
    @GetMapping("/{projectId}/{fileName}")
    public ResponseEntity<FileDto> getFileByProjectId(@PathVariable Long projectId,@PathVariable String fileName) {
        FileDto fileDto = fileService.getFileByProjectId(projectId, fileName);
        return ResponseEntity.ok(fileDto);
    }

    // Build Update File Content REST API
    @PutMapping("{id}/content")
    public ResponseEntity<FileDto> updateFileContent(@PathVariable("id") Long fileId, @RequestBody Map<String, String> body) {
        String content = body.get("content");
        FileDto updated = fileService.updateFileContent(fileId, content);
        return ResponseEntity.ok(updated);
    }

    // Build Delete File REST API
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteFile(@PathVariable("id") Long fileId) {
        fileService.deleteFile(fileId);
        return ResponseEntity.ok("File deleted successfully");
    }
}
