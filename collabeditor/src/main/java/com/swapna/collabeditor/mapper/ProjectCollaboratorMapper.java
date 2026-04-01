package com.swapna.collabeditor.mapper;

import com.swapna.collabeditor.dto.ProjectCollaboratorDto;
import com.swapna.collabeditor.entity.Project;
import com.swapna.collabeditor.entity.ProjectCollaborator;
import com.swapna.collabeditor.entity.User;

public class ProjectCollaboratorMapper {

    // convert project Collab entity to dto
    public static ProjectCollaboratorDto mapToProjectCollaboratorDto(ProjectCollaborator projectCollaborator) {
        return new ProjectCollaboratorDto(
                projectCollaborator.getId(),
                projectCollaborator.getProject().getId(),
                projectCollaborator.getUser().getId(),
                projectCollaborator.getPermission(),
                projectCollaborator.getAddedAt()
        );
    }

    // convert dto to entity,
    public static ProjectCollaborator mapToProjectCollaborator(ProjectCollaboratorDto projectCollaboratorDto, Project project, User user) {
        return new ProjectCollaborator(
                projectCollaboratorDto.getId(),
                project,
                user,
                projectCollaboratorDto.getPermission(),
                projectCollaboratorDto.getAddedAt()
        );
    }
}
