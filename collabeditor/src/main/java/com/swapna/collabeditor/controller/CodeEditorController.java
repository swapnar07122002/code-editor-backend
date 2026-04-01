package com.swapna.collabeditor.controller;

import com.swapna.collabeditor.dto.EditorMessageDto;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class CodeEditorController {

    private final SimpMessagingTemplate messagingTemplate;

    // Client sends to: /app/edit/{roomId}
    // Server broadcasts to: /topic/room/{roomId}
    @MessageMapping("/edit/{roomId}")
    public void receiveEdit(@DestinationVariable String roomId, @Payload EditorMessageDto message) {

        message.setRoomId(roomId);

        // broadcast only to subscribers of the specific room
        messagingTemplate.convertAndSend("/topic/room/" + roomId, message);
    }
}
