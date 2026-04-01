package com.swapna.collabeditor.repository;

import com.swapna.collabeditor.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByOwnerId(Long ownerId);

    List<Project> findByOwnerIdOrderByCreatedAtAsc(Long ownerId);

    List<Project> findTop5ByOwnerIdOrderByUpdatedAtDesc(Long ownerId);

}
