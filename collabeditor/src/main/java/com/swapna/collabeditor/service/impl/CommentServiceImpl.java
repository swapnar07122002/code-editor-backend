package com.swapna.collabeditor.service.impl;

import com.swapna.collabeditor.dto.CommentDto;
import com.swapna.collabeditor.entity.Comment;
import com.swapna.collabeditor.entity.File;
import com.swapna.collabeditor.entity.User;
import com.swapna.collabeditor.exception.ResourceNotFoundException;
import com.swapna.collabeditor.mapper.CommentMapper;
import com.swapna.collabeditor.repository.CommentRepository;
import com.swapna.collabeditor.repository.FileRepository;
import com.swapna.collabeditor.repository.UserRepository;
import com.swapna.collabeditor.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private FileRepository documentRepository;
    private UserRepository userRepository;

    @Override
    public CommentDto addComment(CommentDto commentDto) {

        // Fetch Document id from DB
        File document = documentRepository.findById(commentDto.getDocumentId())
                .orElseThrow(() -> new ResourceNotFoundException("document not found with id: "+commentDto.getDocumentId()));

        // Fetch User entity from DB
        User user = userRepository.findById(commentDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("user not found with id: "+commentDto.getUserId()));

        // convert dto to entity
        Comment comment = CommentMapper.mapToComment(commentDto, document, user);

        // save entity into DB
        Comment savedComment = commentRepository.save(comment);

        // convert entity to dto
        return CommentMapper.mapToCommentDto(savedComment);
    }

    @Override
    public CommentDto getCommentById(Long commentId) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("comment not found with id: "+ commentId));

        // convert comment entity to dto
        return CommentMapper.mapToCommentDto(comment);
    }

    @Override
    public List<CommentDto> getAllComments(Long documentId) {

        List<Comment> comments = commentRepository.findByDocumentId(documentId);
        return comments.stream().map((comment) -> CommentMapper.mapToCommentDto(comment))
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto updateComment(Long commentId, String newContent) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + commentId));

        comment.setContent(newContent);

        Comment updatedComment = commentRepository.save(comment);
        return CommentMapper.mapToCommentDto(updatedComment);
    }

    @Override
    public void deleteComment(Long commentId) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + commentId));

        commentRepository.delete(comment);
    }
}
