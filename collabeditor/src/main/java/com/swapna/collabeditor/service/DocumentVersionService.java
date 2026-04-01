package com.swapna.collabeditor.service;

import com.swapna.collabeditor.dto.DocumentVersionDto;
import com.swapna.collabeditor.dto.UserDto;

import java.util.List;

public interface DocumentVersionService {

    DocumentVersionDto createVersion(DocumentVersionDto documentVersionDto);

    DocumentVersionDto getVersionById(Long versionId);

    List<DocumentVersionDto> getAllVersions();

}
