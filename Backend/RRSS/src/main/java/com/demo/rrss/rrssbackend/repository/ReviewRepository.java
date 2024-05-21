package com.demo.rrss.rrssbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.rrss.rrssbackend.entity.Review;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {
    @Query("SELECT r.productId, COUNT(r) FROM Review r WHERE r.productId IN :productIds GROUP BY r.productId")
    List<Object[]> countReviewsByProductIds(@Param("productIds") List<Long> productIds);

    List<Review> findByUserId(Long userId);

    @Query("SELECT r.productId FROM Review r WHERE r.userId = :userId")
    List<Long> findProductIdsByUserId(@Param("userId") Long userId);
}
