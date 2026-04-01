package com.swapna.collabeditor.mapper;

import com.swapna.collabeditor.dto.CommentDto;
import com.swapna.collabeditor.entity.Comment;
import com.swapna.collabeditor.entity.File;
import com.swapna.collabeditor.entity.User;

public class CommentMapper {

    // convert comment entity to dto
    public static CommentDto mapToCommentDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getDocument().getId(),
                comment.getUser().getId(),
                comment.getContent(),
                comment.getLineNumber(),
                comment.getCreatedAt()
        );
    }

    // convert comment dto to entity
    public static Comment mapToComment(CommentDto commentDto, File document, User user) {
        return new Comment(
                commentDto.getId(),
                document,
                user,
                commentDto.getContent(),
                commentDto.getLineNumber(),
                commentDto.getCreatedAt()
        );
    }
}
