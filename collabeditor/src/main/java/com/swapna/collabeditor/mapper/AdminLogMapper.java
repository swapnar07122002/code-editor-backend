package com.swapna.collabeditor.mapper;

import com.swapna.collabeditor.dto.AdminLogDto;
import com.swapna.collabeditor.entity.AdminLog;
import com.swapna.collabeditor.entity.User;

public class AdminLogMapper {

    // convert entity to dto
    public static AdminLogDto mapToAdminLogDto(AdminLog adminLog) {
        return new AdminLogDto(
                adminLog.getId(),
                adminLog.getAdmin().getId(),
                adminLog.getAction(),
                adminLog.getTargetId(),
                adminLog.getTargetType(),
                adminLog.getTimestamp()
        );
    }

    // convert dto to entity
    public static AdminLog mapToAdminLog(AdminLogDto adminLogDto, User admin) {
        return new AdminLog(
                adminLogDto.getId(),
                admin,
                adminLogDto.getAction(),
                adminLogDto.getTargetId(),
                adminLogDto.getTargetType(),
                adminLogDto.getTimestamp()
        );
    }
}
