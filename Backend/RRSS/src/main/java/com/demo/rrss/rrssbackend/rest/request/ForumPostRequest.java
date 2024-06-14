package com.demo.rrss.rrssbackend.rest.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class ForumPostRequest {

    private Long forumPostId;
    
    private Long userId;

    private Long forumId;

    private String postName;

    private String postData;

}
