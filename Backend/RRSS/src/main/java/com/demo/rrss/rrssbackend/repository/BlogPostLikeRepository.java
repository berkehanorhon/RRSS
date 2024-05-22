package com.demo.rrss.rrssbackend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.demo.rrss.rrssbackend.entity.BlogPostLike;

@Repository
public interface BlogPostLikeRepository extends CrudRepository<BlogPostLike, Long>{
    public Integer getCountByBlogPostId(Long blogPostId);
}
