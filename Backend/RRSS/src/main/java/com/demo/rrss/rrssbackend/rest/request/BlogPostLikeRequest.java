package com.demo.rrss.rrssbackend.rest.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class BlogPostLikeRequest {

    private long userId;

    private long blogPostId;
    
}
