package com.demo.rrss.rrssbackend.rest.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class ReviewRequest {
	private Long reviewId;
	private Long productId;
	private String reviewData;

}