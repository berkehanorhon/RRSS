package com.demo.rrss.rrssbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;

import com.demo.rrss.rrssbackend.entity.BlogPostLike;
import com.demo.rrss.rrssbackend.repository.BlogPostLikeRepository;
import com.demo.rrss.rrssbackend.rest.request.BlogPostLikeRequest;
import com.demo.rrss.rrssbackend.repository.BlogPostRepository;

@Service
public class BlogPostLikeService {
    
    @Autowired
	BlogPostLikeRepository repository;

    @Autowired
    BlogPostRepository blogPostRepository;

    public Integer getBlogPostLikeNumber(BlogPostLikeRequest request){
		if(blogPostRepository.existsById(request.getBlogPostId())){
			return repository.getCountByBlogPostId(request.getBlogPostId());
		}
		else 
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Blog Post not found.");
	}

	public void addBlogPostLike(BlogPostLikeRequest request, Model model) {
		Long userId = (Long) model.getAttribute("userId"); // Not used at the moment
		BlogPostLike blogPostLike = new BlogPostLike();
		blogPostLike.setBlogPostId(request.getBlogPostId());
        blogPostLike.setUserId(userId);
        blogPostLike.setLikeDate(new java.sql.Timestamp(new java.util.Date().getTime()));
		repository.save(blogPostLike);
	}


	public void deleteBlogPostLike(Long blogPostId, Model model) {
		Long userId = (Long) model.getAttribute("userId");
		if (repository.existsByBlogPostId(blogPostId) && repository.findByBlogPostId(blogPostId).getUserId() == userId)
			repository.deleteByBlogPostId(blogPostId);
		else
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Blog post like not found or you do not have permission to delete it.");
	}

}
