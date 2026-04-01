package com.swapna.collabeditor.repository;

import com.swapna.collabeditor.entity.DocumentVersion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentVersionRepository extends JpaRepository<DocumentVersion, Long> {
}
