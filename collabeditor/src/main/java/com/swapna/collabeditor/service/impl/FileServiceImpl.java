package com.swapna.collabeditor.service.impl;

import com.swapna.collabeditor.dto.FileDto;
import com.swapna.collabeditor.entity.File;
import com.swapna.collabeditor.entity.Project;
import com.swapna.collabeditor.entity.User;
import com.swapna.collabeditor.exception.ResourceNotFoundException;
import com.swapna.collabeditor.mapper.FileMapper;
import com.swapna.collabeditor.repository.FileRepository;
import com.swapna.collabeditor.repository.ProjectRepository;
import com.swapna.collabeditor.repository.UserRepository;
import com.swapna.collabeditor.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FileServiceImpl implements FileService {

    private FileRepository fileRepository;
    private UserRepository userRepository;
    private ProjectRepository projectRepository;

    @Override
    public FileDto createFile(FileDto fileDto, Long ownerId, Long projectId) {

        // Fetch User entity from DB
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + ownerId));

        // Fetch Project entity from DB
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + projectId));

        // Check for duplicate filename in the same project
        Optional<File> existingFile = fileRepository.findByProjectIdAndName(projectId, fileDto.getName());
        if (existingFile.isPresent()) {
            throw new IllegalArgumentException("A file with this name already exists in the project.");
        }

        // Extract file extension from file name
        String fileName = fileDto.getName();
        String extension = "unknown";

        if (fileName != null && fileName.contains(".")) {
            String[] parts = fileName.split("\\.");
            extension = parts[parts.length - 1].toLowerCase();

            // Only allow html, css, js — else mark as unknown
            if (!extension.equals("html") && !extension.equals("css") && !extension.equals("js")) {
                extension = "unknown";
            }
        }

        // Convert FileDto to File entity
        File file = FileMapper.mapToFile(fileDto, owner, project);

        // set extension
        file.setExtension(extension);

        // Save File entity into DB
        File savedFile = fileRepository.save(file);

        // Convert saved entity back to DTO
        return FileMapper.mapToFileDto(savedFile);
    }

    @Override
    public FileDto getFileById(Long fileId) {
        File file = fileRepository.findById(fileId)
                .orElseThrow(() -> new ResourceNotFoundException("File not found with id: " + fileId));

        return FileMapper.mapToFileDto(file);
    }

    @Override
    public List<FileDto> getAllFilesByOwner(Long ownerId) {
        List<File> files = fileRepository.findByOwnerId(ownerId);
        return files.stream()
                .map(FileMapper::mapToFileDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<FileDto> getAllFilesByProject(Long projectId) {
        List<File> files = fileRepository.findByProjectId(projectId);
        return files.stream()
                .map(FileMapper::mapToFileDto)
                .collect(Collectors.toList());
    }

    @Override
    public FileDto getFileByProjectId(Long projectId, String fileName) {

        File file = fileRepository.findByProjectIdAndName(projectId, fileName)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "File '" + fileName + "' not found in project " + projectId
                ));

        return FileMapper.mapToFileDto(file);
    }

    @Override
    public FileDto updateFileContent(Long fileId, String content) {
        File file = fileRepository.findById(fileId)
                .orElseThrow(() -> new ResourceNotFoundException("File not found with id: " + fileId));

        file.setContent(content);

        File savedFile = fileRepository.saveAndFlush(file);
        return FileMapper.mapToFileDto(savedFile);
    }

    @Override
    public void deleteFile(Long fileId) {
        File file = fileRepository.findById(fileId)
                .orElseThrow(() -> new ResourceNotFoundException("file not found with id: " + fileId));

        fileRepository.deleteById(fileId);
    }
}
