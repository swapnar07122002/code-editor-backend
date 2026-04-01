package com.swapna.collabeditor.service;

import com.swapna.collabeditor.dto.ProjectDto;

import java.util.List;

public interface ProjectService {

    ProjectDto createProject(ProjectDto projectDto, Long ownerId);

    List<ProjectDto> getProjectsByUser(Long ownerId);

    ProjectDto getProjectById(Long projectId);

    List<ProjectDto> getRecentProjectsByUser(Long ownerId);

    ProjectDto updateProject(Long projectId, ProjectDto updatedProjectDto);

    void deleteProject(Long projectId);
}
