package com.swapna.collabeditor.controller;

import com.swapna.collabeditor.dto.CommentDto;
import com.swapna.collabeditor.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private CommentService commentService;

    // Build Add Comment REST API
    @PostMapping
    public ResponseEntity<CommentDto> addComment(@RequestBody CommentDto commentDto) {
        CommentDto savedComment = commentService.addComment(commentDto);
        return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
    }

    // Build get comment REST API
    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("id") Long commentId) {
        CommentDto comment = commentService.getCommentById(commentId);
        return ResponseEntity.ok(comment);
    }

    // Build Get all comments REST API
    @GetMapping("/document/{documentId}")
    public ResponseEntity<List<CommentDto>> getAllComments(@PathVariable("documentId") Long documentId) {
        List<CommentDto> comments = commentService.getAllComments(documentId);
        return ResponseEntity.ok(comments);
    }

    // Build update comment REST API
    @PutMapping("/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable("id") Long commentId, @RequestBody String newContent) {
        CommentDto updatedComment = commentService.updateComment(commentId, newContent);
        return ResponseEntity.ok(updatedComment);
    }

    // Build Delete comment REST API
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable("id") Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok("Comment deleted successfully with id: " + commentId);
    }
}
