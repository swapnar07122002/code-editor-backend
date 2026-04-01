package com.swapna.collabeditor.service.impl;

import com.swapna.collabeditor.dto.AdminLogDto;
import com.swapna.collabeditor.entity.AdminLog;
import com.swapna.collabeditor.entity.Notification;
import com.swapna.collabeditor.entity.User;
import com.swapna.collabeditor.exception.ResourceNotFoundException;
import com.swapna.collabeditor.mapper.AdminLogMapper;
import com.swapna.collabeditor.repository.AdminLogRepository;
import com.swapna.collabeditor.repository.UserRepository;
import com.swapna.collabeditor.service.AdminLogService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AdminLogServiceImpl implements AdminLogService {

    private AdminLogRepository adminLogRepository;
    private UserRepository userRepository;

    @Override
    public AdminLogDto addAdminLog(AdminLogDto adminLogDto) {

        User admin = userRepository.findById(adminLogDto.getAdminId())
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found with id: " + adminLogDto.getAdminId()));

        AdminLog savedLog = adminLogRepository.save(AdminLogMapper.mapToAdminLog(adminLogDto, admin));

        return AdminLogMapper.mapToAdminLogDto(savedLog);
    }

    @Override
    public List<AdminLogDto> getAllAdminLogs() {

        List<AdminLog> adminLogs = adminLogRepository.findAll();

        return adminLogs.stream().map(AdminLogMapper::mapToAdminLogDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AdminLogDto> getLogsByAdminId(Long adminId) {

        List<AdminLog> adminLogs = adminLogRepository.findByAdminId(adminId);
        return adminLogs.stream().map(AdminLogMapper::mapToAdminLogDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAdminLog(Long adminLogId) {

        AdminLog adminLog = adminLogRepository.findById(adminLogId)
                .orElseThrow(() -> new ResourceNotFoundException("Admin log not found with id: " + adminLogId));

        adminLogRepository.delete(adminLog);
    }
}
