package com.demo.rrss.rrssbackend.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;

import com.demo.rrss.rrssbackend.entity.Message;
import com.demo.rrss.rrssbackend.repository.MessageRepository;
import com.demo.rrss.rrssbackend.rest.request.MessageRequest;
import com.demo.rrss.rrssbackend.rest.request.MessageBoxRequest;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message getMessage(Long messageId) {
        Message message = messageRepository.findByMessageId(messageId);
        return message;
    }

public void saveMessage(MessageRequest messageDTO, Model model) {
    Long userId = (Long) model.getAttribute("userId");
    if (userId.equals(messageDTO.getReceiverId())) {
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

    public List<MessageBoxRequest> getDirectMessageBoxes(Model model) {
        Long userId = (Long) model.getAttribute("userId");
        // Alıcı veya gönderici olarak kullanıcının olduğu tüm mesajları al.
        List<Message> allMessages = messageRepository.findBySenderIdOrReceiverId(userId, userId);

        // Mesajları grupla (buradaki mantığın doğru olup olmadığını test etmemiz lazım)
        Map<Long, List<Message>> messageGroups = allMessages.stream()
                .collect(Collectors.groupingBy(message ->
                        message.getSenderId().equals(userId) ? message.getReceiverId() : message.getSenderId()));

        List<MessageBoxRequest> messageBoxes = messageGroups.entrySet().stream()
                .map(messages -> {
                    MessageBoxRequest messageBox = new MessageBoxRequest();
                    messageBox.setReceiverUserId(messages.getKey());
                    messageBox.setMessages(messages.getValue().stream()
                            .sorted(Comparator.comparing(Message::getSentDate))
                            .map(message -> {
                                Message messageDTO = new Message();
                                messageDTO.setMessageId(message.getMessageId());
                                messageDTO.setSenderId(message.getSenderId());
                                messageDTO.setReceiverId(message.getReceiverId());
                                messageDTO.setMessage(message.getMessage());
                                messageDTO.setSentDate(message.getSentDate());
                                return messageDTO;
                            })
                            .collect(Collectors.toList()));
                    return messageBox;
                })
                .collect(Collectors.toList());

        return messageBoxes;
    }
}
