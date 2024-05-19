package com.demo.rrss.rrssbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import com.demo.rrss.rrssbackend.entity.composite.BlogPostLikeId;

import java.sql.Timestamp;

@Setter
@Getter
@Entity
@Table(name = "BlogPostLike")
@IdClass(BlogPostLikeId.class)
public class BlogPostLike {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Id
    @Column(name = "blog_post_id")
    private Long blogPostId;

    @CreationTimestamp
    @Column(name = "like_date", updatable = false)
    private Timestamp likeDate;


}
