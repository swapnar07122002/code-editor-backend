package com.swapna.collabeditor.service.impl;

import com.swapna.collabeditor.dto.ProjectCollaboratorDto;
import com.swapna.collabeditor.entity.Project;
import com.swapna.collabeditor.entity.ProjectCollaborator;
import com.swapna.collabeditor.entity.User;
import com.swapna.collabeditor.exception.ResourceNotFoundException;
import com.swapna.collabeditor.mapper.ProjectCollaboratorMapper;
import com.swapna.collabeditor.repository.ProjectCollaboratorRepository;
import com.swapna.collabeditor.repository.ProjectRepository;
import com.swapna.collabeditor.repository.UserRepository;
import com.swapna.collabeditor.service.ProjectCollaboratorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProjectCollaboratorServiceImpl implements ProjectCollaboratorService {

    private ProjectCollaboratorRepository projectCollaboratorRepository;
    private ProjectRepository projectRepository;
    private UserRepository userRepository;

    @Override
    public ProjectCollaboratorDto addCollaborator(ProjectCollaboratorDto projectCollaboratorDto) {

        // Fetch Project id from DB
        Project project = projectRepository.findById(projectCollaboratorDto.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("project not found with id: "+projectCollaboratorDto.getProjectId()));

        // Fetch User entity from DB
        User user = userRepository.findById(projectCollaboratorDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("user not found with id: "+projectCollaboratorDto.getUserId()));

        if (projectCollaboratorRepository.findByProjectIdAndUserId(
                projectCollaboratorDto.getProjectId(),
                projectCollaboratorDto.getUserId()).isPresent()) {
            throw new IllegalArgumentException("User is already a collaborator for this project");
        }

        String permission = projectCollaboratorDto.getPermission();
        if (permission == null ||
                (!permission.equals("read") && !permission.equals("write") && !permission.equals("owner"))) {
            throw new IllegalArgumentException("Invalid permission: " + permission);
        }

        // convert dto to entity
        ProjectCollaborator projectCollaborator = ProjectCollaboratorMapper.mapToProjectCollaborator(projectCollaboratorDto, project, user);

        // save entity into DB
        ProjectCollaborator savedProjectCollaborator = projectCollaboratorRepository.save(projectCollaborator);

        // convert entity to dto
        return ProjectCollaboratorMapper.mapToProjectCollaboratorDto(savedProjectCollaborator);
    }

    @Override
    public List<ProjectCollaboratorDto> getCollaboratorsByProjectId(Long projectId) {

        List<ProjectCollaborator> projectCollaborators = projectCollaboratorRepository.findByProjectId(projectId);
        return projectCollaborators.stream().map((projectCollaborator) -> ProjectCollaboratorMapper.mapToProjectCollaboratorDto(projectCollaborator))
                .collect(Collectors.toList());
    }

    @Override
    public ProjectCollaboratorDto updateCollaboratorPermission(Long projectId, Long userId, String newPermission) {

        ProjectCollaborator projectCollaborator = projectCollaboratorRepository.findByProjectIdAndUserId(projectId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Collaborator not found with projectId: " + projectId + " and userId: " + userId));

        projectCollaborator.setPermission(newPermission);

        ProjectCollaborator updatedProjectCollaborator = projectCollaboratorRepository.save(projectCollaborator);
        return ProjectCollaboratorMapper.mapToProjectCollaboratorDto(updatedProjectCollaborator);
    }

    @Override
    public void removeCollaborator(Long projectId, Long userId) {

        ProjectCollaborator projectCollaborator = projectCollaboratorRepository.findByProjectIdAndUserId(projectId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Collaborator not found with projectId: " + projectId + " and userId: " + userId));

        projectCollaboratorRepository.delete(projectCollaborator);
    }


}
