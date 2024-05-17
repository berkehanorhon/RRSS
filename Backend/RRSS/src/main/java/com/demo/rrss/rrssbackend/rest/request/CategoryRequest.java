package com.demo.rrss.rrssbackend.rest.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class CategoryRequest {

	private Long categoryId;

	private String categoryName;

	private String description;
}