package com.demo.rrss.rrssbackend.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.demo.rrss.rrssbackend.entity.BlogPostLike;

@Repository
public interface BlogPostLikeRepository extends CrudRepository<BlogPostLike, Long>{
    
    @Query("SELECT COUNT(f) FROM BlogPostLike f WHERE f.blogPostId = :blogPostId")
    public Integer getCountByBlogPostId(Long blogPostId);

    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM BlogPostLike f WHERE f.blogPostId = :blogPostId")
    public boolean existsByBlogPostId(Long blogPostId);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM BlogPostLike f WHERE f.blogPostId = :blogPostId")
    public void deleteByBlogPostId(Long blogPostId);
    
    @Query("SELECT f FROM BlogPostLike f WHERE f.blogPostId = :blogPostId")
    public BlogPostLike findByBlogPostId(Long blogPostId);

}
