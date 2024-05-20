package com.demo.rrss.rrssbackend.entity.composite;

import java.io.Serializable;
import java.util.Objects;

public class ForumPostLikeId implements Serializable {
    private Long userId;
    private Long forumPostId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ForumPostLikeId that = (ForumPostLikeId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(forumPostId, that.forumPostId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, forumPostId);
    }
}
