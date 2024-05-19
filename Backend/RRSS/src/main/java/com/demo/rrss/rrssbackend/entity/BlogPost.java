package com.demo.rrss.rrssbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.sql.Timestamp;

@Setter
@Getter
@Entity
@Table(name = "BlogPost")
public class BlogPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blog_post_id")
    private Long blogPostId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "post_name", nullable = false)
    private String postName;

    @Column(name = "post_data", nullable = false)
    private String postData;

    @CreationTimestamp
    @Column(name = "publish_date", updatable = false)
    private Timestamp publishDate;

    @Column(name = "image_path")
    private String imagePath;


}
