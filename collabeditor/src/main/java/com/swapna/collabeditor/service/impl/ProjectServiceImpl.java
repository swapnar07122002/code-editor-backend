package com.swapna.collabeditor.service.impl;

import com.swapna.collabeditor.dto.ProjectDto;
import com.swapna.collabeditor.entity.File;
import com.swapna.collabeditor.entity.Project;
import com.swapna.collabeditor.entity.User;
import com.swapna.collabeditor.exception.ResourceNotFoundException;
import com.swapna.collabeditor.mapper.ProjectMapper;
import com.swapna.collabeditor.repository.FileRepository;
import com.swapna.collabeditor.repository.ProjectRepository;
import com.swapna.collabeditor.repository.UserRepository;
import com.swapna.collabeditor.service.ProjectService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private ProjectRepository projectRepository;
    private UserRepository userRepository;
    private FileRepository fileRepository;

    // code templates
    private static final String HTML_BOILERPLATE = """
    <!DOCTYPE html>
    <html lang="en">
      <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>Code Editor</title>
        <link rel="stylesheet" href="@/style.css">
      </head>
      <body>
        <h1>Hello World!</h1>
        <script src="@/script.js"></script>
      </body>
    </html>
    """;

    private static final String CSS_BOILERPLATE = """
    body {
        font-size: 1rem;
    }
    """;

    private static final String JS_BOILERPLATE = """
    console.log("This is js file");
    """;

    @Override
    @Transactional
    public ProjectDto createProject(ProjectDto projectDto, Long ownerId) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + ownerId));

        Project project = ProjectMapper.mapToProject(projectDto, owner);
        Project savedProject = projectRepository.save(project);

        //starter files(only create if they don't already exist in the project
        createStarterFileIfNotExists(savedProject, owner, "index.html", HTML_BOILERPLATE, "html");
        createStarterFileIfNotExists(savedProject, owner, "style.css", CSS_BOILERPLATE, "css");
        createStarterFileIfNotExists(savedProject, owner, "script.js", JS_BOILERPLATE, "js");

        return ProjectMapper.mapToProjectDto(savedProject);
    }

    // Helper to create starter file only if it doesn't exist already
    private void createStarterFileIfNotExists(Project project, User owner, String fileName, String content, String extension) {
        Long projectId = project.getId();

        Optional<File> existing = fileRepository.findByProjectIdAndName(projectId, fileName);
        if (existing.isPresent()) {
            // skip creating duplicate starter file
            return;
        }

        File file = new File();
        file.setName(fileName);
        file.setContent(content);
        file.setExtension(extension);
        file.setOwner(owner);
        file.setProject(project);

        fileRepository.save(file);
    }

    @Override
    public List<ProjectDto> getProjectsByUser(Long ownerId) {
        List<Project> projects = projectRepository.findByOwnerIdOrderByCreatedAtAsc(ownerId);
        return projects.stream().map((project) -> ProjectMapper.mapToProjectDto(project))
                .collect(Collectors.toList());
    }

    @Override
    public ProjectDto getProjectById(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id " + projectId));
        return ProjectMapper.mapToProjectDto(project);
    }

    @Override
    public List<ProjectDto> getRecentProjectsByUser(Long ownerId) {
        List<Project> projects = projectRepository.findTop5ByOwnerIdOrderByUpdatedAtDesc(ownerId);
        return projects.stream().map((project) -> ProjectMapper.mapToProjectDto(project))
                .collect(Collectors.toList());
    }


    @Override
    public ProjectDto updateProject(Long projectId, ProjectDto updatedProjectDto) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id " + projectId));

        project.setName(updatedProjectDto.getName());
        project.setDescription(updatedProjectDto.getDescription());
        project.setIsPublic(updatedProjectDto.getIsPublic());

        Project updated = projectRepository.save(project);
        return ProjectMapper.mapToProjectDto(updated);
    }

    @Override
    public void deleteProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id " + projectId));
        projectRepository.delete(project);
    }
}
