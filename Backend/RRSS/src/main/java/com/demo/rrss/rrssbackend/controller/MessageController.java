package com.demo.rrss.rrssbackend.controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.demo.rrss.rrssbackend.rest.request.MessageBoxRequest;
import com.demo.rrss.rrssbackend.rest.request.MessageRequest;
import com.demo.rrss.rrssbackend.service.MessageService;

@RestController
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/get-message-box")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<MessageBoxRequest>> getMessageBox(@RequestParam Long userId) {
        List<MessageBoxRequest> msgBoxes = messageService.getDirectMessageBoxes(userId);
        return ResponseEntity.ok(msgBoxes);
    }

    @PostMapping("/send-message")
    @ResponseStatus(HttpStatus.OK)
    public void sendMessage(@RequestBody MessageRequest messageRequest) {
        messageService.saveMessage(messageRequest);
    }

    @GetMapping("/get-message")
    public ResponseEntity<MessageRequest> getMessage(@RequestParam Long messageId) {
        MessageRequest message = messageService.getMessage(messageId);
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/delete-message")
    public void deleteMessage(@RequestParam Long messageId) {
        messageService.deleteMessage(messageId);
    }
    
}
