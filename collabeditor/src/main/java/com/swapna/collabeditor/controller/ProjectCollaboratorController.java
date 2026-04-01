package com.swapna.collabeditor.controller;

import com.swapna.collabeditor.dto.ProjectCollaboratorDto;
import com.swapna.collabeditor.service.ProjectCollaboratorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("api/projects/{projectId}/collaborators")
public class ProjectCollaboratorController {

    private ProjectCollaboratorService projectCollaboratorService;

    // Build Add Project Collaborator REST API
    @PostMapping
    public ResponseEntity<ProjectCollaboratorDto> addCollaborator(@PathVariable("projectId") Long projectId, @RequestBody ProjectCollaboratorDto projectCollaboratorDto) {
        // set projectId from URL into DTO
        projectCollaboratorDto.setProjectId(projectId);

        ProjectCollaboratorDto savedProjectCollaborator = projectCollaboratorService.addCollaborator(projectCollaboratorDto);

        return new ResponseEntity<>(savedProjectCollaborator, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProjectCollaboratorDto>>  getCollaboratorsByProjectId(@PathVariable("projectId") Long projectId) {
        List<ProjectCollaboratorDto> projectCollaborators = projectCollaboratorService.getCollaboratorsByProjectId(projectId);
        return ResponseEntity.ok(projectCollaborators);
    }


    @PutMapping("/{userId}")
    public ResponseEntity<ProjectCollaboratorDto> updateCollaboratorPermission(@PathVariable("projectId") Long projectId, @PathVariable("userId") Long userId, @RequestBody Map<String, String> requestBody) {

        String newPermission = requestBody.get("permission");
        ProjectCollaboratorDto updatedCollaborator = projectCollaboratorService.updateCollaboratorPermission(projectId, userId, newPermission);

        return ResponseEntity.ok(updatedCollaborator);
    }


    @DeleteMapping("/{userId}")
    public ResponseEntity<String> removeCollaborator(@PathVariable("projectId") Long projectId, @PathVariable("userId") Long userId) {

        projectCollaboratorService.removeCollaborator(projectId, userId);
        return ResponseEntity.ok("Collaborator removed successfully");
    }
}
