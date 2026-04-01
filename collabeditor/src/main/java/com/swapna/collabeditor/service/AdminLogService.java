package com.swapna.collabeditor.service;

import com.swapna.collabeditor.dto.AdminLogDto;

import java.util.List;

public interface AdminLogService {

    AdminLogDto addAdminLog(AdminLogDto adminLogDto);

    List<AdminLogDto> getAllAdminLogs();

    List<AdminLogDto> getLogsByAdminId(Long adminId);

    void deleteAdminLog(Long logId);
}
