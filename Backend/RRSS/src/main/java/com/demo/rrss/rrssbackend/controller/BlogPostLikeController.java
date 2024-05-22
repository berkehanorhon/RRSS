package com.demo.rrss.rrssbackend.controller;

import org.springframework.web.bind.annotation.RestController;
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

import com.demo.rrss.rrssbackend.entity.BlogPostLike;
import com.demo.rrss.rrssbackend.rest.request.BlogPostLikeRequest;
import com.demo.rrss.rrssbackend.rest.request.ForumPostLikeRequest;
import com.demo.rrss.rrssbackend.service.JwtUtil;
import com.demo.rrss.rrssbackend.service.BlogPostLikeService;
import com.demo.rrss.rrssbackend.service.ForumPostLikeService;

public class BlogPostLikeController {

    @Autowired
	private JwtUtil jwtUtil;

	@Autowired
	BlogPostLikeService service;

	@ModelAttribute // TODO Herhangi bir hata durumunda 403 döndürülecek
	public void addUserIdToModel(@RequestHeader(value="Authorization") String bearerToken, Model model) {
		String token = bearerToken.substring(7);
		Long userId = jwtUtil.extractUserId(token);
		model.addAttribute("userId", userId);
	}

	@GetMapping(value = "/get-blog-post-like-number")
	@ResponseStatus(HttpStatus.OK)
	public Integer getBlogPostLikeNumber(@RequestParam BlogPostLikeRequest blogPostLike){
		return service.getBlogPostLikeNumber(blogPostLike);
	}

	
	@PostMapping(value = "/add-blog-post-like", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public void addBlogPostLike(@RequestBody BlogPostLikeRequest blogPostLike, Model model) {
		service.addBlogPostLike(blogPostLike, model);
	}


	@DeleteMapping(value = "/delete-blog-post-like")
	@ResponseStatus(HttpStatus.OK)
	public void deleteBlogPostLike(@RequestParam Long blogPostLikeId, Model model) {
		service.deleteBlogPostLike(blogPostLikeId, model);
	}
}
