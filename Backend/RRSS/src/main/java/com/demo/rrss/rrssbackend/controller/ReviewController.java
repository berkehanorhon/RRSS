package com.demo.rrss.rrssbackend.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.demo.rrss.rrssbackend.entity.Review;
import com.demo.rrss.rrssbackend.rest.request.ReviewRequest;
import com.demo.rrss.rrssbackend.service.ReviewService;

@RestController
public class ReviewController {

	@Autowired
	ReviewService service;

	@GetMapping("/get-review")
	@ResponseStatus(HttpStatus.OK)
	public Review getReview(@RequestParam Long reviewId) {
		return service.getReview(reviewId);
	}

	@GetMapping("/get-all-reviews")
	@ResponseStatus(HttpStatus.OK)
	public List<Review> getAllReviews() {
		List<Review> reviews = service.getAllReviews();
		return reviews.stream().limit(10).collect(Collectors.toList());
	}

	@PostMapping(value = "/add-review", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public void addReview(@RequestBody ReviewRequest review) {
		service.addReview(review);
	}

	@PatchMapping(value = "/update-review")
	@ResponseStatus(HttpStatus.OK)
	public void updateReview(@RequestBody ReviewRequest review) {
		service.updateReview(review.getReviewId(), review);
	}

	@DeleteMapping(value = "/delete-review")
	@ResponseStatus(HttpStatus.OK)
	public void deleteReview(@RequestParam Long reviewId) {
		service.deleteReview(reviewId);
	}
}