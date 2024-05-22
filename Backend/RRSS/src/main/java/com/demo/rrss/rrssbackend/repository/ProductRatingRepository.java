package com.demo.rrss.rrssbackend.repository;

import com.demo.rrss.rrssbackend.entity.ProductRating;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRatingRepository extends JpaRepository<ProductRating, Long> {
    @Query("SELECT r.productId FROM ProductRating r WHERE r.userId = :userId AND r.starRating >= :starRating")
    List<Long> findProductIdsByUserIdAndStarRatingGreaterThanEqual(@Param("userId") Long userId, @Param("starRating") short starRating);

    @Query("SELECT r.productId, AVG(r.starRating) FROM ProductRating r WHERE r.productId IN :productIds GROUP BY r.productId")
    List<Object[]> findAverageRatingsByProductIds(@Param("productIds") List<Long> productIds);

    @Query("SELECT AVG(pr.starRating) FROM ProductRating pr WHERE pr.productId = :productId")
    Optional<Float> findAverageRatingByProductId(@Param("productId") Long productId);

    @Query("SELECT COUNT(pr) FROM ProductRating pr WHERE pr.productId = :productId")
    Long findRatingCountByProductId(@Param("productId") Long productId);

    default Float getAverageRatingOrZero(Long productId) {
        return findAverageRatingByProductId(productId).orElse(0.0f);
    }
}
