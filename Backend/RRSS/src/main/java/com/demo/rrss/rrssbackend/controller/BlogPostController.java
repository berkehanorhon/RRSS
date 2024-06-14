package com.demo.rrss.rrssbackend.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.demo.rrss.rrssbackend.entity.BlogPost;
import com.demo.rrss.rrssbackend.rest.request.BlogPostRequest;
import com.demo.rrss.rrssbackend.service.JwtUtil;
import com.demo.rrss.rrssbackend.service.BlogPostService;

@RestController
public class BlogPostController {

    @Autowired
	private JwtUtil jwtUtil;

	@Autowired
	BlogPostService service;

	@ModelAttribute
	public void addUserIdToModel(@RequestHeader(value="Authorization", required=false) String bearerToken, Model model) {
		if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Authorization header is missing or invalid");
		}
		String token = bearerToken.substring(7);
		Long userId;
		try {
			userId = jwtUtil.extractUserId(token);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid token");
		}
		if (userId == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User ID not found in token");
		}
		model.addAttribute("userId", userId);
	}

	@GetMapping("/get-blog-post")
	@ResponseStatus(HttpStatus.OK)
	public BlogPost getBlogPost(@RequestParam Long blogPostId) {
		return service.getBlogPost(blogPostId);
	}

	@GetMapping("/get-all-blog-posts")
	@ResponseStatus(HttpStatus.OK)
	public List<BlogPost> getAllBlogPosts() {
		return service.getAllBlogPosts();
	}

	@GetMapping("/get-users-all-blog-posts")
	@ResponseStatus(HttpStatus.OK)
	public List<BlogPost> getAllBlogPosts(@RequestParam Long userId) {
		return service.getUsersAllBlogPosts(userId);
	}

	@PostMapping(value = "/add-blog-post", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public void addBlogPost(@RequestBody BlogPostRequest blogPost, Model model) {
		service.addBlogPost(blogPost,model);
	}

	@PatchMapping(value = "/update-blog-post")
	@ResponseStatus(HttpStatus.OK)
	public void updateBlogPost(@RequestBody BlogPostRequest blogPost, Model model) {
		service.updateBlogPost(blogPost.getBlogPostId(), blogPost, model);
	}

	@DeleteMapping(value = "/delete-blog-post")
	@ResponseStatus(HttpStatus.OK)
	public void deleteBlogPost(@RequestParam Long blogPostId, Model model) {
		service.deleteBlogPost(blogPostId, model);
	}
}
