package com.swapna.collabeditor.service;

import com.swapna.collabeditor.dto.MessageDto;

import java.util.List;

public interface MessageService {

    MessageDto sendMessage(MessageDto messageDto);

    List<MessageDto> getMessagesByDocument(Long documentId);

    void deleteMessage(Long messageId);
}
