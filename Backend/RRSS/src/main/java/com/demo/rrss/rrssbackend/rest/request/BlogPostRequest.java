package com.demo.rrss.rrssbackend.rest.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class BlogPostRequest {

    private long userId;

    private long blogPostId;

    private String postName;

    private String postData;

    private String imagePath;

}
