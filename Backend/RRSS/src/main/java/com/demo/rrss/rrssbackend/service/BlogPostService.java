package com.demo.rrss.rrssbackend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;

import com.demo.rrss.rrssbackend.entity.BlogPost;
import com.demo.rrss.rrssbackend.repository.BlogPostRepository;
import com.demo.rrss.rrssbackend.rest.request.BlogPostRequest;

@Service
public class BlogPostService {
    @Autowired
	BlogPostRepository repository;

	public BlogPost getBlogPost(Long blogPostId) {
		Optional<BlogPost> response = repository.findById(blogPostId);
		return response.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Blog post not found"));
	}

	public void addBlogPost(BlogPostRequest request, Model model) {
		Long userId = (Long) model.getAttribute("userId"); // Not used at the moment
		BlogPost blogPost = new BlogPost();
		blogPost.setBlogPostId(request.getBlogPostId());
        blogPost.setPostData(request.getPostData());
        blogPost.setImagePath(request.getImagePath());
        blogPost.setPostName(request.getPostName());
        blogPost.setUserId(userId);
		blogPost.setPublishDate(new java.sql.Timestamp(new java.util.Date().getTime()));
		repository.save(blogPost);
	}

	public void updateBlogPost(Long blogPostId, BlogPostRequest request, Model model) {
		Optional<BlogPost> existingBlogPost = repository.findById(blogPostId);
		Long userId = (Long) model.getAttribute("userId");
		if (existingBlogPost.isPresent() && existingBlogPost.get().getUserId() == userId){
			BlogPost blogPost = existingBlogPost.get();
			blogPost.setPostData(request.getPostData());
			blogPost.setPublishDate(new java.sql.Timestamp(new java.util.Date().getTime()));
            blogPost.setImagePath(request.getImagePath());
            blogPost.setPostName(request.getPostName());
			repository.save(blogPost);
		} else {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Blog post not found or you do not have permission to update it.");
		}
	}

	public void deleteBlogPost(Long blogPostId, Model model) {
		Long userId = (Long) model.getAttribute("userId");
		if (repository.existsById(blogPostId) && repository.findById(blogPostId).get().getUserId() == userId)
			repository.deleteById(blogPostId);
		else
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Blog post not found or you do not have permission to delete it.");
	}

	public List<BlogPost> getAllBlogPosts() {
		return (List<BlogPost>) repository.findAll();
	}
}
