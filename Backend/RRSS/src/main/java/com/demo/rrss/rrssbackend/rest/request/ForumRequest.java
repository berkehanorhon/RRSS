package com.demo.rrss.rrssbackend.rest.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data

public class ForumRequest {

    private long forumId;

    private String forumDescription;

    private String forumName;

}
