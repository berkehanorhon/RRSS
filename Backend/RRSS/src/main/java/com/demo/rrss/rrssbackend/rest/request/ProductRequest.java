package com.demo.rrss.rrssbackend.rest.request;

import java.sql.Timestamp;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class ProductRequest {

	private Long categoryId;

	private String description;

	private Long productId;

	private String title;

	private Long userId;

	private Float star;

	private Timestamp publishDate;

	private String imagePath;
}
