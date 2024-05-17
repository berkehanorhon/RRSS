package com.demo.rrss.rrssbackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Product {

	@Column(name = "CATEGORY_ID")
	private Long categoryId;

	@Column(name = "DESCRIPTION")
	private String description;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PRODUCT_ID")
	private Long productId;

	@Column(name = "PUBLISH_DATE")
	private java.sql.Timestamp publishDate;

	@Column(name = "TITLE")
	private String title;

	@Column(name = "USER_ID")
	private Long userId;

}
