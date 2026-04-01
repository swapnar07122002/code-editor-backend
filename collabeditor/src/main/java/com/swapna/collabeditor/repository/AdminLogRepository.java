package com.swapna.collabeditor.repository;

import com.swapna.collabeditor.entity.AdminLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminLogRepository extends JpaRepository<AdminLog, Long> {
    List<AdminLog> findByAdminId(Long adminId);
}
