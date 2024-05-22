package com.demo.rrss.rrssbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.rrss.rrssbackend.entity.ReviewRating;

@Repository
public interface ReviewRatingRepository extends CrudRepository<ReviewRating, Long> {

}
