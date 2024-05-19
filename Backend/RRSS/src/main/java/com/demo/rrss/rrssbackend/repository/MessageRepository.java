package com.demo.rrss.rrssbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.rrss.rrssbackend.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>{
    Message findByMessageId(Long messageId);
    List<Message> findByReceiverId(Long receiverId);
    List<Message> findBySenderId(Long senderId);
    List<Message> findBySenderIdAndReceiverId(Long senderId, Long receiverId);
    List<Message> findBySenderIdOrReceiverId(Long senderId, Long receiverId);
}
