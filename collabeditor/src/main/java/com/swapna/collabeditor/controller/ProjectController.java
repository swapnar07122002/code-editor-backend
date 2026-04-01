package com.swapna.collabeditor.controller;

import com.swapna.collabeditor.dto.ProjectDto;
import com.swapna.collabeditor.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/projects")
@AllArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping("/create/{ownerId}")
    public ResponseEntity<?> createProject(
            @PathVariable Long ownerId,
            @RequestBody ProjectDto projectDto) {
        ProjectDto createdProject = projectService.createProject(projectDto, ownerId);
        return ResponseEntity.ok(Map.of(
                "message", "Project created successfully",
                "project", createdProject
        ));
    }

    @GetMapping("/user/{ownerId}")
    public ResponseEntity<List<ProjectDto>> getUserProjects(@PathVariable Long ownerId) {
        List<ProjectDto> projects = projectService.getProjectsByUser(ownerId);
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDto> getProjectById(@PathVariable Long id) {
        ProjectDto project = projectService.getProjectById(id);
        return ResponseEntity.ok(project);
    }

    @GetMapping("/user/{ownerId}/recent")
    public ResponseEntity<List<ProjectDto>> getRecentProjects(@PathVariable Long ownerId) {
        List<ProjectDto> recentProjects = projectService.getRecentProjectsByUser(ownerId);
        return ResponseEntity.ok(recentProjects);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateProject(
            @PathVariable Long id,
            @RequestBody ProjectDto projectDto) {
        ProjectDto updated = projectService.updateProject(id, projectDto);
        return ResponseEntity.ok(Map.of(
                "message", "Project updated successfully",
                "project", updated
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.ok("Project deleted successfully");
    }
}
