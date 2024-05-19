package com.demo.rrss.rrssbackend.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

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

    public MessageRequest getMessage(Long messageId) {
        Message message = messageRepository.findByMessageId(messageId);
        MessageRequest messageDTO = new MessageRequest();
        messageDTO.setMessageId(message.getMessageId());
        messageDTO.setSenderId(message.getSenderId());
        messageDTO.setReceiverId(message.getReceiverId());
        messageDTO.setMessage(message.getMessage());
        messageDTO.setSendTime(message.getSendTime());
        return messageDTO;
    }

    public List<MessageRequest> getMessagesByReceiverId(Long receiverId) {
        List<Message> msgList = messageRepository.findByReceiverId(receiverId);
        List<MessageRequest> msgDTOList = new ArrayList<>();
        for (Message message : msgList) {
            MessageRequest messageDTO = new MessageRequest();
            messageDTO.setMessageId(message.getMessageId());
            messageDTO.setSenderId(message.getSenderId());
            messageDTO.setReceiverId(message.getReceiverId());
            messageDTO.setMessage(message.getMessage());
            messageDTO.setSendTime(message.getSendTime());
            msgDTOList.add(messageDTO);
        }
        return msgDTOList;
    }

    public List<MessageRequest> getMessagesBySenderId(Long senderId) {
        List<Message> msgList = messageRepository.findBySenderId(senderId);
        List<MessageRequest> msgDTOList = new ArrayList<>();
        for (Message message : msgList) {
            MessageRequest messageDTO = new MessageRequest();
            messageDTO.setMessageId(message.getMessageId());
            messageDTO.setSenderId(message.getSenderId());
            messageDTO.setReceiverId(message.getReceiverId());
            messageDTO.setMessage(message.getMessage());
            messageDTO.setSendTime(message.getSendTime());
            msgDTOList.add(messageDTO);
        }
        return msgDTOList;
    }

    public void saveMessage(MessageRequest messageDTO) {
        Message message = new Message();
        message.setMessageId(messageDTO.getMessageId());
        message.setSenderId(messageDTO.getSenderId());
        message.setReceiverId(messageDTO.getReceiverId());
        message.setMessage(messageDTO.getMessage());
        message.setSendTime(messageDTO.getSendTime());
        messageRepository.save(message);
    }

    public void deleteMessage(Long messageId) {
        messageRepository.deleteById(messageId);
    }

    public List<MessageBoxRequest> getDirectMessageBoxes(Long userId) {
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
                            .sorted(Comparator.comparing(Message::getSendTime))
                            .map(message -> {
                                MessageRequest messageDTO = new MessageRequest();
                                messageDTO.setMessageId(message.getMessageId());
                                messageDTO.setSenderId(message.getSenderId());
                                messageDTO.setReceiverId(message.getReceiverId());
                                messageDTO.setMessage(message.getMessage());
                                messageDTO.setSendTime(message.getSendTime());
                                return messageDTO;
                            })
                            .collect(Collectors.toList()));
                    return messageBox;
                })
                .collect(Collectors.toList());

        return messageBoxes;
    }
}
