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

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", insertable = false, updatable = false)
    private Product product;
}
