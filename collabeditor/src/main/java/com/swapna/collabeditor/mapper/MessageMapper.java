package com.swapna.collabeditor.mapper;

import com.swapna.collabeditor.dto.MessageDto;
import com.swapna.collabeditor.entity.File;
import com.swapna.collabeditor.entity.Message;
import com.swapna.collabeditor.entity.User;

public class MessageMapper {

    // convert message entity to dto
    public static MessageDto mapToMessageDto(Message message) {
        return new MessageDto(
                message.getId(),
                message.getDocument().getId(),
                message.getSender().getId(),
                message.getContent(),
                message.getSentAt()
        );
    }

    public static Message mapToMessage(MessageDto messageDto, File document, User sender) {
        return new Message(
                messageDto.getId(),
                document,
                sender,
                messageDto.getContent(),
                messageDto.getSentAt()
        );
    }
}
