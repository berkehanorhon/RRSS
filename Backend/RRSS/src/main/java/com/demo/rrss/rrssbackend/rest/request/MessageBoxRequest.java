package com.demo.rrss.rrssbackend.rest.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data

public class MessageBoxRequest {
    private String username;
    private String lastMessage;
    private String lastMessageTime;
}
