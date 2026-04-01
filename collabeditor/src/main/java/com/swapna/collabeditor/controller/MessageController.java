package com.swapna.collabeditor.controller;

import com.swapna.collabeditor.dto.MessageDto;
import com.swapna.collabeditor.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/documents/{documentId}/messages")
public class MessageController {

    private  MessageService messageService;

    // Build Add Message REST API
    @PostMapping
    public ResponseEntity<MessageDto> sendMessage(@PathVariable("documentId") Long documentId, @RequestBody MessageDto messageDto) {
        messageDto.setDocumentId(documentId);
        MessageDto savedMessage = messageService.sendMessage(messageDto);
        return new ResponseEntity<>(savedMessage, HttpStatus.CREATED);
    }

    // Build all messages REST API
    @GetMapping
    public ResponseEntity<List<MessageDto>> getMessages(@PathVariable("documentId") Long documentId) {
        return ResponseEntity.ok(messageService.getMessagesByDocument(documentId));
    }

    // Build delete message REST API
    @DeleteMapping("/{messageId}")
    public ResponseEntity<String> deleteMessage(@PathVariable("messageId") Long messageId) {
        messageService.deleteMessage(messageId);
        return ResponseEntity.ok("Message deleted successfully");
    }
}
