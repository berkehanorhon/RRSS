package com.demo.rrss.rrssbackend.rest.request;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import jakarta.annotation.Nullable;

@Getter
@Setter
@Data
public class MessageRequest {
    
    @Nullable
    private Long messageId;

    private Long senderId;

    private Long receiverId;

    private String message;

    @Nullable
    private LocalDateTime sendTime = LocalDateTime.now();
}
