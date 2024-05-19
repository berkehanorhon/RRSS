package com.demo.rrss.rrssbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import com.demo.rrss.rrssbackend.entity.composite.ForumPostLikeId;

import java.sql.Timestamp;

@Setter
@Getter
@Entity
@IdClass(ForumPostLikeId.class)
public class ForumPostLike {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Id
    @Column(name = "forum_post_id")
    private Long forumPostId;

    @CreationTimestamp
    @Column(name = "like_date", updatable = false)
    private Timestamp likeDate;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "forum_post_id", referencedColumnName = "forum_post_id", insertable = false, updatable = false)
    private ForumPost forumPost;
}
