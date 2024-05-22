package com.demo.rrss.rrssbackend.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.demo.rrss.rrssbackend.entity.Review;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {
    List<Review> findByUserId(Long userId);

    List<Review> findAllByProductId(Long productId);
}
