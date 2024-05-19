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

    private Long receiverId;

    private String message;
}
