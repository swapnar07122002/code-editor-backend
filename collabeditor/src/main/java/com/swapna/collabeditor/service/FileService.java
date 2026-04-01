package com.swapna.collabeditor.service;

import com.swapna.collabeditor.dto.FileDto;

import java.util.List;

public interface FileService {

    FileDto createFile(FileDto fileDto,Long ownerId, Long projectId);

    FileDto getFileById(Long fileId);

    List<FileDto> getAllFilesByOwner(Long ownerId);

    List<FileDto> getAllFilesByProject(Long projectId);

    FileDto getFileByProjectId(Long projectId, String fileName);

    FileDto updateFileContent(Long fileId, String content);

    void deleteFile(Long fileId);
}
