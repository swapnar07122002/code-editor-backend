package com.swapna.collabeditor.service;

import com.swapna.collabeditor.dto.ProjectCollaboratorDto;

import java.util.List;

public interface ProjectCollaboratorService {

    ProjectCollaboratorDto addCollaborator(ProjectCollaboratorDto projectCollaboratorDto);

    List<ProjectCollaboratorDto> getCollaboratorsByProjectId(Long projectId);

    ProjectCollaboratorDto updateCollaboratorPermission(Long projectId, Long userId, String newPermission);

    void removeCollaborator(Long projectId, Long userId);
}
