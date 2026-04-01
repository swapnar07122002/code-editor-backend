package com.swapna.collabeditor.mapper;

import com.swapna.collabeditor.dto.CodeExecutionDto;
import com.swapna.collabeditor.entity.CodeExecution;
import com.swapna.collabeditor.entity.File;
import com.swapna.collabeditor.entity.User;

public class CodeExecutionMapper {

    // convert entity to dto
    public static CodeExecutionDto mapToCodeExecutionDto(CodeExecution codeExecution) {
        return new CodeExecutionDto(
                codeExecution.getId(),
                codeExecution.getDocument().getId(),
                codeExecution.getUser().getId(),
                codeExecution.getInput(),
                codeExecution.getOutput(),
                codeExecution.getErrors(),
                codeExecution.getStatus(),
                codeExecution.getExecutedAt()
        );
    }

    // convert dto to entity
    public static CodeExecution mapToCodeExecution(CodeExecutionDto codeExecutionDto, File document, User user) {
        return new CodeExecution(
                codeExecutionDto.getId(),
                document,
                user,
                codeExecutionDto.getInput(),
                codeExecutionDto.getOutput(),
                codeExecutionDto.getErrors(),
                codeExecutionDto.getStatus(),
                codeExecutionDto.getExecutedAt()
        );
    }
}
