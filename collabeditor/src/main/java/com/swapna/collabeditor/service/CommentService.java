package com.swapna.collabeditor.service;

import com.swapna.collabeditor.dto.CommentDto;
import com.swapna.collabeditor.dto.UserDto;

import java.util.List;

public interface CommentService {

    CommentDto addComment(CommentDto commentDto);

    CommentDto getCommentById(Long commentId);

    List<CommentDto> getAllComments(Long documentId);

    CommentDto updateComment(Long commentId, String newContent);

    void deleteComment(Long commentId);
}
