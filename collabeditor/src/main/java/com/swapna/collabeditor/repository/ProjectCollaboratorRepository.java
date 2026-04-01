package com.swapna.collabeditor.repository;

import com.swapna.collabeditor.entity.ProjectCollaborator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectCollaboratorRepository extends JpaRepository<ProjectCollaborator, Long> {

    // fetch collaborators by project id
    List<ProjectCollaborator> findByProjectId(Long projectId);


    // fetch project and user id
    Optional<ProjectCollaborator> findByProjectIdAndUserId(Long projectId, Long userId);

}
