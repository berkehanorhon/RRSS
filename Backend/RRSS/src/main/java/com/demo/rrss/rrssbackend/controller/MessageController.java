package com.demo.rrss.rrssbackend.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.demo.rrss.rrssbackend.entity.Message;
import com.demo.rrss.rrssbackend.rest.request.ConversationRequest;
import com.demo.rrss.rrssbackend.rest.request.MessageBoxRequest;
import com.demo.rrss.rrssbackend.rest.request.MessageRequest;
import com.demo.rrss.rrssbackend.service.MessageService;
import com.demo.rrss.rrssbackend.service.JwtUtil;

@RestController
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }
 
    
    @Autowired
	private JwtUtil jwtUtil;

    @ModelAttribute
	public void addUserIdToModel(@RequestHeader(value="Authorization", required=false) String bearerToken, Model model) {
		if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Authorization header is missing or invalid");
		}
		String token = bearerToken.substring(7);
		Long userId;
		try {
			userId = jwtUtil.extractUserId(token);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid token");
		}
		if (userId == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User ID not found in token");
		}
		model.addAttribute("userId", userId);
	}

    @GetMapping("/get-message-box")
    @ResponseStatus(HttpStatus.OK)
    public List<Map<Long, MessageBoxRequest>> getMessageBox(Model model) {
        return messageService.getDirectMessageBoxes(model);
    }

    @PostMapping("/send-message")
    @ResponseStatus(HttpStatus.OK)
    public void sendMessage(@RequestBody MessageRequest messageRequest,Model model) {
        messageService.saveMessage(messageRequest,model);
    }

    @PostMapping("/get-conversation-with-user")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ConversationRequest> getConversation(@RequestParam Long userId, Model model) {
        return ResponseEntity.ok(messageService.getConversation(userId, model));
    }

    @GetMapping("/get-message")
    @ResponseStatus(HttpStatus.OK) // TODO model'den userid çekilerek mesajı görme yetkisi kontrol edilecek
    public ResponseEntity<Message> getMessage(@RequestParam Long messageId, Model model) {
        Message message = messageService.getMessage(messageId);
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/delete-message")
    @ResponseStatus(HttpStatus.OK) // TODO model'den userid çekilerek mesajı silme yetkisi kontrol edilecek
    public void deleteMessage(@RequestParam Long messageId, Model model) {
        messageService.deleteMessage(messageId);
    }
    
}
