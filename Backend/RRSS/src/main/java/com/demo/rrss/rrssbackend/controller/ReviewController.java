package com.demo.rrss.rrssbackend.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.demo.rrss.rrssbackend.entity.Review;
import com.demo.rrss.rrssbackend.rest.request.ReviewRequest;
import com.demo.rrss.rrssbackend.service.JwtUtil;
import com.demo.rrss.rrssbackend.service.ReviewService;

@RestController
public class ReviewController {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	ReviewService service;

	@ModelAttribute
	public void addUserIdToModel(@RequestHeader(value="Authorization", required=false) String bearerToken, Model model) {
		if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Authorization header is missing or invalid");
		}
		String token = bearerToken.substring(7);
		Long userId;
		try {
			userId = jwtUtil.extractUserId(token);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid token");
		}
		if (userId == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User ID not found in token");
		}
		model.addAttribute("userId", userId);
	}

	@GetMapping("/get-review")
	@ResponseStatus(HttpStatus.OK)
	public HashMap<String, Object> getReview(@RequestParam Long reviewId) {
		return service.getReview(reviewId);
	}

	@GetMapping("/get-review-given-star")
	@ResponseStatus(HttpStatus.OK)
	public Short getReviewGivenStar(@RequestParam Long productId, @RequestParam Long userId) {
		return service.getReviewGivenStar(productId, userId);
	}

	@GetMapping("/get-all-reviews")
	@ResponseStatus(HttpStatus.OK)
	public List<HashMap<String, Object>> getAllReviews() {
		return service.getAllReviews();
	}

	@GetMapping("/get-users-reviews")
	@ResponseStatus(HttpStatus.OK)
	public List<Review> getUsersAllReviews(@RequestParam Long userId) {
        return service.getUsersAllReviews(userId);
	}

	@GetMapping("/get-products-reviews")
	@ResponseStatus(HttpStatus.OK)
	public List<Review> getProductsAllReviews(@RequestParam Long productId) {
        return service.getProductsAllReviews(productId);
	}

	@PostMapping(value = "/add-review", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public void addReview(@RequestParam Short star, @RequestBody ReviewRequest review, Model model) {
		service.addReview(review,model,star);
	}

	@PatchMapping(value = "/update-review")
	@ResponseStatus(HttpStatus.OK)
	public void updateReview(@RequestBody ReviewRequest review, Model model) {
		service.updateReview(review.getReviewId(), review, model);
	}

	@DeleteMapping(value = "/delete-review")
	@ResponseStatus(HttpStatus.OK)
	public void deleteReview(@RequestParam Long reviewId, Model model) {
		service.deleteReview(reviewId, model);
	}
}