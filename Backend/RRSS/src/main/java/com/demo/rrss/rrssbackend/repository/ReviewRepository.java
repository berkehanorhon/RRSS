package com.demo.rrss.rrssbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.rrss.rrssbackend.entity.Review;

import jakarta.transaction.Transactional;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {
    @Query("SELECT r.productId, COUNT(r) FROM Review r WHERE r.productId IN :productIds GROUP BY r.productId")
    List<Object[]> countReviewsByProductIds(@Param("productIds") List<Long> productIds);
    Review findByUserIdAndProductId(Long userId, Long productId);
    List<Review> findByUserId(Long userId);
    List<Review> findAllByProductId(Long productId);
    @Query("SELECT r.productId FROM Review r WHERE r.userId = :userId")
    List<Long> findProductIdsByUserId(@Param("userId") Long userId);

    @Modifying
    @Transactional
    public void deleteByReviewId(Long reviewId);
    @Query(nativeQuery = true, value = "SELECT * FROM Review ORDER BY publish_date DESC LIMIT 50")
    List<Review> findAllMax50();
}
