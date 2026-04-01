package com.swapna.collabeditor.repository;

import com.swapna.collabeditor.entity.CodeExecution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CodeExecutionRepository extends JpaRepository<CodeExecution, Long> {

    List<CodeExecution> findByDocumentId(Long documentId);

    List<CodeExecution> findByUserId(Long userId);
}
