package com.swapna.collabeditor.controller;

import com.swapna.collabeditor.dto.DocumentVersionDto;
import com.swapna.collabeditor.service.DocumentVersionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/documents/{documentId}/versions")
public class DocumentVersionController {

    private DocumentVersionService documentVersionService;

    // Build Add Document versions REST API
    @PostMapping
    public ResponseEntity<DocumentVersionDto> createVersion(@PathVariable("documentId") Long documentId, @RequestBody DocumentVersionDto documentVersionDto) {
        // set document id from URL into DTO
        documentVersionDto.setDocumentId(documentId);

        DocumentVersionDto savedDocumentVersion = documentVersionService.createVersion(documentVersionDto);

        return new ResponseEntity<>(savedDocumentVersion, HttpStatus.CREATED);
    }

    // Build Get Document Version REST API
    @GetMapping("{id}")
    public ResponseEntity<DocumentVersionDto> getVersionById(@PathVariable("id") Long versionId) {
        DocumentVersionDto documentVersionDto = documentVersionService.getVersionById(versionId);
        return ResponseEntity.ok(documentVersionDto);
    }

    // Build Get All Versions REST API
    @GetMapping
    public ResponseEntity<List<DocumentVersionDto>> getAllVersions(){
        List<DocumentVersionDto> versions = documentVersionService.getAllVersions();
        return ResponseEntity.ok(versions);
    }
}
