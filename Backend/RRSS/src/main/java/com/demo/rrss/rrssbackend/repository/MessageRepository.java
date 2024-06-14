package com.demo.rrss.rrssbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.rrss.rrssbackend.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>{
    Message findByMessageId(Long messageId);
    List<Message> findByReceiverId(Long receiverId);
    List<Message> findBySenderId(Long senderId);
    List<Message> findBySenderIdAndReceiverId(Long senderId, Long receiverId);
    List<Message> findBySenderIdOrReceiverId(Long senderId, Long receiverId);
    @Query("SELECT DISTINCT m FROM Message m WHERE (m.senderId = :senderId1 AND m.receiverId = :receiverId1) OR (m.senderId = :senderId2 AND m.receiverId = :receiverId2) ORDER BY m.sentDate ASC")
    List<Message> findBySenderIdAndReceiverIdOrSenderIdAndReceiverIdDistinct(@Param("senderId1") Long senderId1, @Param("receiverId1") Long receiverId1, @Param("senderId2") Long senderId2, @Param("receiverId2") Long receiverId2);
}
