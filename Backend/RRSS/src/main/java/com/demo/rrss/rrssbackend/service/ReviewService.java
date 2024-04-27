package com.demo.rrss.rrssbackend.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
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

	public void addReview(ReviewRequest request) {
		Review review = new Review();
		review.setProductId(request.getProductId());
		review.setUserId(request.getUserId());
		review.setReviewData(request.getReviewData());

		String dateString = request.getPublishDate();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			java.util.Date utilDate = format.parse(dateString);
			java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
			review.setPublishDate(sqlDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		repository.save(review);
	}

	public void updateReview(Long reviewId, ReviewRequest request) {
		Optional<Review> existingReview = repository.findById(reviewId);
		if (existingReview.isPresent()) {
			Review review = existingReview.get();
			review.setProductId(request.getProductId());
			review.setUserId(request.getUserId());
			review.setReviewData(request.getReviewData());

			String dateString = request.getPublishDate();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			try {
				java.util.Date utilDate = format.parse(dateString);
				java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
				review.setPublishDate(sqlDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			repository.save(review);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found");
		}
	}

	public void deleteReview(Long reviewId) {
		if (repository.existsById(reviewId))
			repository.deleteById(reviewId);
		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found");
	}

	public List<Review> getAllReviews() {
		return (List<Review>) repository.findAll();
	}
}