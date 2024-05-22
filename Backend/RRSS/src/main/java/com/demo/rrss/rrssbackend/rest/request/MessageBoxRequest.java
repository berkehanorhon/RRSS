package com.demo.rrss.rrssbackend.rest.request;

import java.util.List;

import com.demo.rrss.rrssbackend.entity.Message;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data

public class MessageBoxRequest {
    private Long receiverUserId;
    private List<Message> messages;
}
