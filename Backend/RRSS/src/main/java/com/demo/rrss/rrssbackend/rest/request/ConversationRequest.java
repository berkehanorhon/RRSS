package com.demo.rrss.rrssbackend.rest.request;

import java.util.HashMap;
import java.util.List;

import org.hibernate.mapping.Map;

import com.demo.rrss.rrssbackend.entity.Message;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConversationRequest {
    private HashMap<Long, String> users;
    private List<Message> messages;
}
