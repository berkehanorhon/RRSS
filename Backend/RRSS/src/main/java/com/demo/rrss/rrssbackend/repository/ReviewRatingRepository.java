package com.demo.rrss.rrssbackend.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.rrss.rrssbackend.entity.ReviewRating;

import java.util.Optional;

@Repository
public interface ReviewRatingRepository extends CrudRepository<ReviewRating, Long> {

    @Query("SELECT AVG(rr.starRating) FROM ReviewRating rr WHERE rr.reviewId = :reviewId")
    Optional<Float> getAverageRatingOrZero(@Param("reviewId") Long reviewId);

    @Query("SELECT COUNT(rr.starRating) FROM ReviewRating rr WHERE rr.reviewId = :reviewId")
    Optional<Integer> findRatingCountByReviewId(@Param("reviewId") Long reviewId);
}
