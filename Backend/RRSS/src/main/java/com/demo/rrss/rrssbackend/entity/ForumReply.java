package com.demo.rrss.rrssbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.sql.Timestamp;

@Setter
@Getter
@Entity
@Table(name = "ForumReply")
public class ForumReply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "forum_reply_id")
    private Long forumReplyId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "forum_post_id")
    private Long forumPostId;

    @Column(name = "post_data")
    private String postData;

    @CreationTimestamp
    @Column(name = "creation_date", updatable = false)
    private Timestamp creationDate;

}
