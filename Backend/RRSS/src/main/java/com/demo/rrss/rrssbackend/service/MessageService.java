package com.demo.rrss.rrssbackend.service;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;

import com.demo.rrss.rrssbackend.entity.Message;
import com.demo.rrss.rrssbackend.repository.MessageRepository;
import com.demo.rrss.rrssbackend.rest.request.MessageRequest;
import com.demo.rrss.rrssbackend.rest.request.ConversationRequest;
import com.demo.rrss.rrssbackend.rest.request.MessageBoxRequest;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    @Autowired
    UsersService userService;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message getMessage(Long messageId) {
        return messageRepository.findByMessageId(messageId);
    }


public void saveMessage(MessageRequest messageDTO, Model model) {
    Long userId = (Long) model.getAttribute("userId");
    if (Objects.equals(userId, messageDTO.getReceiverId())) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can not send message to yourself.");
    }
    Message message = new Message();
    message.setSenderId(userId);
    message.setReceiverId(messageDTO.getReceiverId());
    message.setMessage(messageDTO.getMessage());
    messageRepository.save(message);
}

    public void deleteMessage(Long messageId) {
        messageRepository.deleteById(messageId);
    }

    public List<Map<Long, MessageBoxRequest>> getDirectMessageBoxes(Model model) {
        Long userId = (Long) model.getAttribute("userId");
        List<Message> allMessages = messageRepository.findBySenderIdOrReceiverId(userId, userId);
        Map<Long, List<Message>> messageGroups = allMessages.stream()
                .collect(Collectors.groupingBy(message ->
                        message.getSenderId().equals(userId) ? message.getReceiverId() : message.getSenderId()));

        List<Map<Long, MessageBoxRequest>> messageBoxes = new ArrayList<>();
        for (Map.Entry<Long, List<Message>> entry : messageGroups.entrySet()) {
            Long otherUserId = entry.getKey();
            List<Message> messages = entry.getValue();

            Message lastMessage = messages.stream()
                    .max(Comparator.comparing(Message::getSentDate))
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No messages found"));

            MessageBoxRequest messageBox = new MessageBoxRequest();
            messageBox.setUsername(userService.getUser(otherUserId).getUsername());
            messageBox.setLastMessage(lastMessage.getMessage());
            messageBox.setLastMessageTime(lastMessage.getSentDate().toString());

            Map<Long, MessageBoxRequest> map = new HashMap<>();
            map.put(otherUserId, messageBox);
            messageBoxes.add(map);
        }

        messageBoxes.sort((box1, box2) -> {
            MessageBoxRequest messageBox1 = box1.values().iterator().next();
            MessageBoxRequest messageBox2 = box2.values().iterator().next();
            return messageBox2.getLastMessageTime().compareTo(messageBox1.getLastMessageTime());
        });

        return messageBoxes;
    }

    public ConversationRequest getConversation(Long userId, Model model) {
        Long currentUserId = (Long) model.getAttribute("userId");
        List<Message> messages = messageRepository.findBySenderIdAndReceiverIdOrSenderIdAndReceiverIdDistinct(currentUserId, userId, userId, currentUserId);
        ConversationRequest conversation = new ConversationRequest();
        HashMap<Long, String> users = new HashMap<>();
        users.put(currentUserId, userService.getUser(currentUserId).getUsername());
        users.put(userId, userService.getUser(userId).getUsername());
        conversation.setUsers(users);
        conversation.setMessages(messages);
        return conversation;
    }
}
