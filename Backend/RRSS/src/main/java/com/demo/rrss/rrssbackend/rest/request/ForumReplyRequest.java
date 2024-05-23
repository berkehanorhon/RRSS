package com.demo.rrss.rrssbackend.rest.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class ForumReplyRequest {

    private long forumReplyId;

    private long forumPostId;

    private long userId;

    private String postData;
    
}
