package com.rrss2024.RRSS.model;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "product_id")
    private Long productId;

    @Column(nullable = false, name = "user_id")
    private Long userId;

    @Column(nullable = false, name = "category_id")
    private Long categoryId;

    @Column(nullable = false, name = "title")
    private String title;

    @Column(nullable = false, name = "description")
    private String description;

    @CreationTimestamp
    @Column(updatable = true, name = "publish_date")
    private LocalDate publishDate;

}
