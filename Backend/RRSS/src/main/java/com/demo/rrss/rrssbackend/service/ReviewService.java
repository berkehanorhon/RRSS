package com.demo.rrss.rrssbackend.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.demo.rrss.rrssbackend.entity.Coupon;
import com.demo.rrss.rrssbackend.repository.CouponRepository;
import com.demo.rrss.rrssbackend.repository.UserBalanceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;

import com.demo.rrss.rrssbackend.entity.Review;
import com.demo.rrss.rrssbackend.repository.ReviewRepository;
import com.demo.rrss.rrssbackend.rest.request.ReviewRequest;

@Service
public class ReviewService {
	private final ReviewRepository repository;
	private final UserBalanceRepository balanceRepository;
	private final CouponRepository couponRepository;

	public ReviewService(ReviewRepository repository, UserBalanceRepository balanceRepository, CouponRepository couponRepository) {
		this.repository = repository;
		this.balanceRepository = balanceRepository;
		this.couponRepository = couponRepository;
	}

	public Review getReview(Long reviewId) {
		Optional<Review> response = repository.findById(reviewId);
		return response.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found"));
	}

	@Transactional
	public void addReview(ReviewRequest request, Model model) {
		Long userId = (Long) model.getAttribute("userId");
		saveReview(request, userId);
		handleUserBalance(userId);
	}

	private void saveReview(ReviewRequest request, Long userId) {
		Review review = new Review();
		review.setProductId(request.getProductId());
		review.setUserId(userId);
		review.setReviewData(request.getReviewData());
		review.setPublishDate(new java.sql.Timestamp(new java.util.Date().getTime()));
		repository.save(review);
	}

	private void handleUserBalance(Long userId) {
		BigDecimal balance = balanceRepository.findBalanceByUserId(userId);
		if (balance != null) {
			BigDecimal thresholdValue = BigDecimal.valueOf(500);
			if (balance.compareTo(thresholdValue) >= 0) {
				createCoupon(userId);
				balance = balance.subtract(thresholdValue);
				balanceRepository.updateUserBalanceByUserId(userId, balance);
			}
		} else {
			balanceRepository.updateUserBalanceByUserId(userId, BigDecimal.ZERO);
		}
	}

	private void createCoupon(Long userId) {
		Coupon coupon = new Coupon();
		coupon.setUserId(userId);
		coupon.setCouponData("You have earned a coupon for your review. Reach out to the merchants to use your coupon!");
		couponRepository.save(coupon);
	}

	public void updateReview(Long reviewId, ReviewRequest request, Model model) {
		Optional<Review> existingReview = repository.findById(reviewId);
		Long userId = (Long) model.getAttribute("userId");
		if (existingReview.isPresent() && Objects.equals(existingReview.get().getUserId(), userId)){
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
		Optional<Review> reviewOptional = repository.findById(reviewId);
		if (reviewOptional.isPresent()) {
			Review review = reviewOptional.get();
			if (Objects.equals(review.getUserId(), userId)) {
				repository.deleteById(reviewId);
			}
		}
		else
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Review not found or you do not have permission to delete it.");
	}

	public List<Review> getAllReviews() {
		return (List<Review>) repository.findAll();
	}

    public List<Review> getUsersAllReviews(Long userId) {
        return repository.findByUserId(userId);
    }

    public List<Review> getProductsAllReviews(Long productId) {
        return repository.findAllByProductId(productId);
    }
}