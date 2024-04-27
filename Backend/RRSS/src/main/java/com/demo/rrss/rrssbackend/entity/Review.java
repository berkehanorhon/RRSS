package com.demo.rrss.rrssbackend.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "review")
@Setter
@Getter
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "review_id")
	private Long reviewId;

	@Column(name = "product_id")
	private Long productId;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "publish_date")
	private Date publishDate;

	@Column(name = "review_data")
	private String reviewData;

}