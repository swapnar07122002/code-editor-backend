package com.swapna.collabeditor.service.impl;

import com.swapna.collabeditor.dto.MessageDto;
import com.swapna.collabeditor.entity.File;
import com.swapna.collabeditor.entity.Message;
import com.swapna.collabeditor.entity.User;
import com.swapna.collabeditor.exception.ResourceNotFoundException;
import com.swapna.collabeditor.mapper.MessageMapper;
import com.swapna.collabeditor.repository.FileRepository;
import com.swapna.collabeditor.repository.MessageRepository;
import com.swapna.collabeditor.repository.UserRepository;
import com.swapna.collabeditor.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {

    private MessageRepository messageRepository;
    private FileRepository documentRepository;
    private UserRepository userRepository;

    @Override
    public MessageDto sendMessage(MessageDto messageDto) {

        // fetch document id from DB
        File document = documentRepository.findById(messageDto.getDocumentId())
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with id: " + messageDto.getDocumentId()));

        // fetch sender(user) id from DB
        User sender = userRepository.findById(messageDto.getSenderId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + messageDto.getSenderId()));

        // convert dto to entity
        Message message = MessageMapper.mapToMessage(messageDto, document, sender);

        // save entity into DB
        Message savedMessage = messageRepository.save(message);

        // convert entity to dto
        return MessageMapper.mapToMessageDto(savedMessage);
    }

    @Override
    public List<MessageDto> getMessagesByDocument(Long documentId) {

        List<Message> messages = messageRepository.findByDocumentId(documentId);

        return messages.stream().map((message) -> MessageMapper.mapToMessageDto(message))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteMessage(Long messageId) {

        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new ResourceNotFoundException("Message not found with id: " + messageId));

        messageRepository.delete(message);
    }
}
