package com.demo.rrss.rrssbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.sql.Timestamp;

@Setter
@Getter
@Entity
@Table(name = "ForumPost")
public class ForumPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "forum_post_id")
    private Long forumPostId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "forum_id")
    private Long forumId;

    @Column(name = "post_name")
    private String postName;

    @Column(name = "post_data")
    private String postData;

    @CreationTimestamp
    @Column(name = "creation_date", updatable = false)
    private Timestamp creationDate;


}
