package com.demo.rrss.rrssbackend.entity.composite;

import java.io.Serializable;
import java.util.Objects;

public class BlogPostLikeId implements Serializable {
    private Long userId;
    private Long blogPostId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlogPostLikeId that = (BlogPostLikeId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(blogPostId, that.blogPostId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, blogPostId);
    }
}
