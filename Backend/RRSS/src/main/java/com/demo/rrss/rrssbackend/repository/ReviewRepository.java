package com.demo.rrss.rrssbackend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.demo.rrss.rrssbackend.entity.Review;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {

}
