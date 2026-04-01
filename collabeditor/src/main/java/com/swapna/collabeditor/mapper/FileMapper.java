package com.swapna.collabeditor.mapper;


import com.swapna.collabeditor.dto.FileDto;
import com.swapna.collabeditor.entity.File;
import com.swapna.collabeditor.entity.Project;
import com.swapna.collabeditor.entity.User;

public class FileMapper {

    // convert file entity to file dto
    public static FileDto mapToFileDto(File file) {
        return new FileDto(
                file.getId(),
                file.getOwner().getId(),
                file.getProject().getId(),
                file.getName(),
                file.getContent(),
                file.getExtension(),
                file.getCreatedAt(),
                file.getUpdatedAt()
        );
    }

    // convert file dto to file entity
    public static File mapToFile(FileDto fileDto, User owner, Project project) {
        return new File(
                fileDto.getId(),
                owner,
                project,
                fileDto.getName(),
                fileDto.getContent(),
                fileDto.getExtension(),
                fileDto.getCreatedAt(),
                fileDto.getUpdatedAt()
        );
    }
}

