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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.demo.rrss.rrssbackend.entity.ForumPost;
import com.demo.rrss.rrssbackend.rest.request.ForumPostRequest;
import com.demo.rrss.rrssbackend.service.JwtUtil;
import com.demo.rrss.rrssbackend.service.ForumPostService;

@RestController
@RequestMapping("/forum")
public class ForumPostController {

    @Autowired
	private JwtUtil jwtUtil;

	@Autowired
	ForumPostService service;

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

	@GetMapping("/get-post")
	@ResponseStatus(HttpStatus.OK)
	public ForumPost getReview(@RequestParam Long forumPostId) {
		return service.getForumPost(forumPostId);
	}

	@GetMapping("/get-all-posts") //TODO en son postlar (maxSize)
	@ResponseStatus(HttpStatus.OK)
	public List<ForumPost> getAllForumPosts() {
		List<ForumPost> forumPosts = service.getAllForumPosts();
		return forumPosts.stream().limit(10).collect(Collectors.toList());
	}

	@PostMapping(value = "/add-post", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public void addForumPost(@RequestBody ForumPostRequest forumPost, Model model) {
		service.addForumPost(forumPost,model);
	}

	@PatchMapping(value = "/update-post")
	@ResponseStatus(HttpStatus.OK)
	public void updateForumPost(@RequestBody ForumPostRequest forumPost, Model model) {
		service.updateForumPost(forumPost.getForumPostId(), forumPost, model);
	}

	@DeleteMapping(value = "/delete-post")
	@ResponseStatus(HttpStatus.OK)
	public void deleteForumPost(@RequestParam Long forumPostId, Model model) {
		service.deleteForumPost(forumPostId, model);
	}
}
