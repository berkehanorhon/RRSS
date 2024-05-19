package com.demo.rrss.rrssbackend.rest.request;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data

public class MessageBoxRequest {
    private Long receiverUserId;
    private List<MessageRequest> messages;
}
