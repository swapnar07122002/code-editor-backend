package com.swapna.collabeditor.mapper;

import com.swapna.collabeditor.dto.ProjectDto;
import com.swapna.collabeditor.entity.Project;
import com.swapna.collabeditor.entity.User;

import java.util.stream.Collectors;

public class ProjectMapper {

    // convert Project entity to Project Dto
    public static ProjectDto mapToProjectDto(Project project) {
        return new ProjectDto(
                project.getId(),
                project.getOwner().getId(),
                project.getName(),
                project.getDescription(),
                project.getIsPublic(),
                project.getCreatedAt(),
                project.getUpdatedAt(),
                project.getDocuments() != null
                        ? project.getDocuments().stream().map(doc -> doc.getId()).collect(Collectors.toList())
                        : null
        );
    }

    // convert ProjectDto to Project entity
    public static Project mapToProject(ProjectDto projectDto, User owner) {
        return new Project(
                projectDto.getId(),
                owner,
                projectDto.getName(),
                projectDto.getDescription(),
                projectDto.getIsPublic(),
                projectDto.getCreatedAt(),
                projectDto.getUpdatedAt(),
                null
        );
    }
}
