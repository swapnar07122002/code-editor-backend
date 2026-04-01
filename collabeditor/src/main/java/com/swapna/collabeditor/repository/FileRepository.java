package com.swapna.collabeditor.repository;

import com.swapna.collabeditor.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Long> {

    // fetch files by owner id
    List<File> findByOwnerId(Long ownerId);

    Optional<File> findByProjectIdAndName(Long projectId, String name);

    // fetch all files inside a specific project
    List<File> findByProjectId(Long projectId);

}
