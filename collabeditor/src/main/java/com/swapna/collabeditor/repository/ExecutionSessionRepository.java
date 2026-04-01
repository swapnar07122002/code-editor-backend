package com.swapna.collabeditor.repository;

import com.swapna.collabeditor.entity.ExecutionSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExecutionSessionRepository extends JpaRepository<ExecutionSession, Long> {

    List<ExecutionSession> findByDocumentId(Long documentId);

    List<ExecutionSession> findByUserId(Long userId);
}
