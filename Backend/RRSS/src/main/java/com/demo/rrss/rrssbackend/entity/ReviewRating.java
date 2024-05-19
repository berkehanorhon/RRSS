package com.demo.rrss.rrssbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import com.demo.rrss.rrssbackend.entity.composite.ReviewRatingId;

import java.sql.Timestamp;

@Setter
@Getter
@Entity
@IdClass(ReviewRatingId.class)
public class ReviewRating {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Id
    @Column(name = "review_id")
    private Long reviewId;

    @CreationTimestamp
    @Column(name = "timestamp", updatable = false)
    private Timestamp timestamp;

    @Column(name = "star_rating", nullable = false)
    private Short starRating;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "review_id", referencedColumnName = "review_id", insertable = false, updatable = false)
    private Review review;
}
