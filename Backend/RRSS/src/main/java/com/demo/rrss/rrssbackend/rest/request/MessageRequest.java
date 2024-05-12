package com.demo.rrss.rrssbackend.rest.request;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class MessageRequest {
    
    private Long messageId;

    private Long senderId;

    private Long receiverId;

    private String message;

    private LocalDateTime sendTime;
}
