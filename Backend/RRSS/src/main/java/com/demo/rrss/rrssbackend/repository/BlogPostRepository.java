package com.demo.rrss.rrssbackend.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.demo.rrss.rrssbackend.entity.BlogPost;

@Repository
public interface BlogPostRepository extends CrudRepository<BlogPost, Long> {
    List<BlogPost> findByUserId(Long userId);
}
