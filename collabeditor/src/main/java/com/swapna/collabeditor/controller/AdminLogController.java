package com.swapna.collabeditor.controller;

import com.swapna.collabeditor.dto.AdminLogDto;
import com.swapna.collabeditor.service.AdminLogService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin-logs")
@AllArgsConstructor
public class AdminLogController {

    private AdminLogService adminLogService;

    @PostMapping
    public ResponseEntity<AdminLogDto> addAdminLog(@RequestBody AdminLogDto adminLogDto) {
        AdminLogDto savedLog = adminLogService.addAdminLog(adminLogDto);
        return new ResponseEntity<>(savedLog, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AdminLogDto>> getAllAdminLogs() {
        List<AdminLogDto> adminLogs = adminLogService.getAllAdminLogs();
        return ResponseEntity.ok(adminLogs);
    }

    @GetMapping("/admin/{adminId}")
    public ResponseEntity<List<AdminLogDto>> getLogsByAdmin(@PathVariable("adminId") Long adminId) {
        List<AdminLogDto> adminLogs = adminLogService.getLogsByAdminId(adminId);
        return ResponseEntity.ok(adminLogs);
    }

    @DeleteMapping("/{logId}")
    public ResponseEntity<String> deleteAdminLog(@PathVariable("logId") Long adminLogId) {
        adminLogService.deleteAdminLog(adminLogId);
        return ResponseEntity.ok("Admin log deleted successfully");
    }
}
