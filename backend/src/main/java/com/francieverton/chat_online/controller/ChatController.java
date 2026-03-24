package com.francieverton.chat_online.controller;

import com.francieverton.chat_online.entity.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/topic/send")
    public ChatMessage sendMessage (ChatMessage chatMessage){
        return chatMessage;
    }
}
