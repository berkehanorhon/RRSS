package com.demo.rrss.rrssbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.sql.Timestamp;

@Setter
@Getter
@Entity
@Table(name = "Bookmark")
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id")
    private Long bookmarkId;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "bookmark_list_id", nullable = false)
    private Long bookmarkListId;

    @CreationTimestamp
    @Column(name = "creation_date", updatable = false)
    private Timestamp creationDate;

}
