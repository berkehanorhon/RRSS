package com.demo.rrss.rrssbackend.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.demo.rrss.rrssbackend.rest.request.ForumPostLikeRequest;
import com.demo.rrss.rrssbackend.service.JwtUtil;
import com.demo.rrss.rrssbackend.service.ForumPostLikeService;

@RestController
public class ForumPostLikeController {

    @Autowired
	private JwtUtil jwtUtil;

	@Autowired
	ForumPostLikeService service;

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

	@GetMapping(value = "/get-forum-post-like-number")
	@ResponseStatus(HttpStatus.OK)
	public Integer getForumPostLikeNumber(@RequestBody ForumPostLikeRequest forumPostLike){
		return service.getForumPostLikeNumber(forumPostLike);
	}

	
	@PostMapping(value = "/add-forum-post-like", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public void addForumPostLike(@RequestBody ForumPostLikeRequest forumPostLike, Model model) {
		service.addForumPostLike(forumPostLike,model);
	}


	//@DeleteMapping(value = "/delete-forum-post-like")
	//@ResponseStatus(HttpStatus.OK)
	//public void deleteForumPostLike(@RequestParam Long forumPostId, Model model) {
	//	service.deleteForumPostLike(forumPostId, model);
	//}

}
