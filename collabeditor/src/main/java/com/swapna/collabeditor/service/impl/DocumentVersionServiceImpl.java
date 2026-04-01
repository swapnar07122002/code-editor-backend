package com.swapna.collabeditor.service.impl;

import com.swapna.collabeditor.dto.DocumentVersionDto;
import com.swapna.collabeditor.entity.File;
import com.swapna.collabeditor.entity.DocumentVersion;
import com.swapna.collabeditor.entity.User;
import com.swapna.collabeditor.exception.ResourceNotFoundException;
import com.swapna.collabeditor.mapper.DocumentVersionMapper;
import com.swapna.collabeditor.repository.FileRepository;
import com.swapna.collabeditor.repository.DocumentVersionRepository;
import com.swapna.collabeditor.repository.UserRepository;
import com.swapna.collabeditor.service.DocumentVersionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DocumentVersionServiceImpl implements DocumentVersionService {

    private DocumentVersionRepository documentVersionRepository;
    private FileRepository documentRepository;
    private UserRepository userRepository;

    @Override
    public DocumentVersionDto createVersion(DocumentVersionDto documentVersionDto) {

        // Fetch Document id from DB
        File document = documentRepository.findById(documentVersionDto.getDocumentId())
                .orElseThrow(() -> new ResourceNotFoundException("document not found with id: "+documentVersionDto.getDocumentId()));

        // Fetch User entity from DB
        User editor = userRepository.findById(documentVersionDto.getEditorId())
                .orElseThrow(() -> new ResourceNotFoundException("editor not found with id: "+documentVersionDto.getEditorId()));

        // convert dto to entity
        DocumentVersion documentVersion = DocumentVersionMapper.mapToDocumentVersion(documentVersionDto, document, editor);

        // save entity into DB
        DocumentVersion savedDocumentVersion = documentVersionRepository.save(documentVersion);

        // convert entity to dto
        return DocumentVersionMapper.mapToDocumentVersionDto(savedDocumentVersion);
    }

    @Override
    public DocumentVersionDto getVersionById(Long versionId) {

        DocumentVersion version = documentVersionRepository.findById(versionId)
                .orElseThrow(() -> new ResourceNotFoundException("version not found with id: "+ versionId));

        // convert document version entity to dto
        return DocumentVersionMapper.mapToDocumentVersionDto(version);
    }

    @Override
    public List<DocumentVersionDto> getAllVersions() {

        List<DocumentVersion> versions = documentVersionRepository.findAll();

        return versions.stream().map((version) -> DocumentVersionMapper.mapToDocumentVersionDto(version))
                .collect(Collectors.toList());
    }

}
