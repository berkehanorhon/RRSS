package com.demo.rrss.rrssbackend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;

import com.demo.rrss.rrssbackend.entity.Review;
import com.demo.rrss.rrssbackend.repository.ReviewRepository;
import com.demo.rrss.rrssbackend.rest.request.ReviewRequest;

@Service
public class ReviewService {
	@Autowired
	ReviewRepository repository;

	public Review getReview(Long reviewId) {
		Optional<Review> response = repository.findById(reviewId);
		return response.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found"));
	}

	public void addReview(ReviewRequest request, Model model) {
		Long userId = (Long) model.getAttribute("userId"); // Not used at the moment
		Review review = new Review();
		review.setProductId(request.getProductId());
		review.setUserId(userId);
		review.setReviewData(request.getReviewData());
		review.setPublishDate(new java.sql.Timestamp(new java.util.Date().getTime()));
		repository.save(review);
	}

	public void updateReview(Long reviewId, ReviewRequest request, Model model) {
		Optional<Review> existingReview = repository.findById(reviewId);
		Long userId = (Long) model.getAttribute("userId");
		if (existingReview.isPresent() && existingReview.get().getUserId() == userId){
			Review review = existingReview.get();
			review.setReviewData(request.getReviewData());
			review.setPublishDate(new java.sql.Timestamp(new java.util.Date().getTime()));
			repository.save(review);
		} else {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Review not found or you do not have permission to update it.");
		}
	}

	public void deleteReview(Long reviewId, Model model) {
		Long userId = (Long) model.getAttribute("userId");
		if (repository.existsById(reviewId) && repository.findById(reviewId).get().getUserId() == userId)
			repository.deleteById(reviewId);
		else
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Review not found or you do not have permission to delete it.");
	}

	public List<Review> getAllReviews() {
		return (List<Review>) repository.findAll();
	}

    public List<Review> getUsersAllReviews(Long userId) {
        return repository.findByUserId(userId);
    }
}