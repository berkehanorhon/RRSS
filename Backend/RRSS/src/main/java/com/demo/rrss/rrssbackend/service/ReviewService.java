package com.demo.rrss.rrssbackend.service;

import java.math.BigDecimal;
import java.util.*;

import com.demo.rrss.rrssbackend.entity.Coupon;
import com.demo.rrss.rrssbackend.entity.ProductRating;
import com.demo.rrss.rrssbackend.repository.CouponRepository;
import com.demo.rrss.rrssbackend.repository.ProductRatingRepository;
import com.demo.rrss.rrssbackend.repository.ReviewRatingRepository;
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
	private final ReviewRatingRepository reviewRatingRepository;
	private final ProductRatingRepository ProductRatingRepo;
  
	public ReviewService(ReviewRepository repository, ReviewRatingRepository reviewRatingRepository,
						 UserBalanceRepository balanceRepository, CouponRepository couponRepository,
						 ProductRatingRepository ProductRatingRepo) {
		this.repository = repository;
		this.reviewRatingRepository = reviewRatingRepository;
		this.balanceRepository = balanceRepository;
		this.couponRepository = couponRepository;
		this.ProductRatingRepo = ProductRatingRepo;
	}

	private HashMap<String, Object> addFieldsToReview(Review response) {
		HashMap<String, Object> reviewMap = new HashMap<>();
		reviewMap.put("reviewId", response.getReviewId());
		reviewMap.put("productId", response.getProductId());
		reviewMap.put("userId", response.getUserId());
		reviewMap.put("reviewData", response.getReviewData());
		reviewMap.put("publishDate", response.getPublishDate());
		Float rating = reviewRatingRepository.getAverageRatingOrZero(response.getReviewId()).orElse(0.0f);
		Integer ratingCount = reviewRatingRepository.findRatingCountByReviewId(response.getReviewId()).orElse(0);
		reviewMap.put("rating", rating);
		reviewMap.put("ratingCount", ratingCount);
		return reviewMap;
	}

	public HashMap<String, Object> getReview(Long reviewId) {
		Review response = repository.findById(reviewId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found"));
        return addFieldsToReview(response);
	}

	@Transactional
	public void addReview(ReviewRequest request, Model model, Short star) {
		Long userId = (Long) model.getAttribute("userId");
		ProductRating productRating = new ProductRating();
		if (star < 1 || star > 5)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Star rating must be between 1 and 5.");
		Review existingReview = repository.findByUserIdAndProductId(userId, request.getProductId());
		if (existingReview != null)
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You have already reviewed this product.");
		saveReview(request, userId);
		repository.findByUserIdAndProductId(userId, request.getProductId());
		productRating.setUserId(userId);
		productRating.setProductId(request.getProductId());
		productRating.setStarRating(star);
		ProductRatingRepo.save(productRating);
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
		coupon.setCouponData(UUID.randomUUID().toString().replace("-", "").substring(0, 12));
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

	public List<HashMap<String, Object>> getAllReviews() {
		List<Review> reviews = repository.findAllMax50();
		List<HashMap<String, Object>> response = new ArrayList<>();
		for (Review review : reviews) {
			response.add(addFieldsToReview(review));
		}
		return response;
	}

	// TODO Yukarıdaki list<hashMap<String, Object>> şekline çevirebiliriz. İhtiyaca bağlı.
    public List<Review> getUsersAllReviews(Long userId) {
        return repository.findByUserId(userId);
    }

	// TODO Yukarıdaki list<hashMap<String, Object>> şekline çevirebiliriz. İhtiyaca bağlı.
	public List<Review> getProductsAllReviews(Long productId) {
        return repository.findAllByProductId(productId);
    }
}