package com.demo.rrss.rrssbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import com.demo.rrss.rrssbackend.entity.composite.ProductRatingId;

import java.sql.Timestamp;

@Setter
@Getter
@Entity
@Table(name = "ProductRating")
@IdClass(ProductRatingId.class)
public class ProductRating {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Id
    @Column(name = "product_id")
    private Long productId;

    @CreationTimestamp
    @Column(name = "rating_date", updatable = false)
    private Timestamp ratingDate;

    @Column(name = "star_rating", nullable = false)
    private Short starRating;

}
