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

import com.demo.rrss.rrssbackend.entity.Forum;
import com.demo.rrss.rrssbackend.rest.request.ForumRequest;
import com.demo.rrss.rrssbackend.service.ForumService;
import com.demo.rrss.rrssbackend.service.JwtUtil;
@RestController
public class ForumController {

    @Autowired
	ForumService service;

	@Autowired
    JwtUtil jwtUtil;

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

	@GetMapping("/get-forum")
	@ResponseStatus(HttpStatus.OK)
	public Forum ForumResponse(@RequestParam Long forumId) {
		return service.getForum(forumId);
	}

	@GetMapping("/get-all-forums")
	@ResponseStatus(HttpStatus.OK)
	public List<Forum> getAllForums(Model model) {
		List<Forum> forums = service.getAllForums(model);
		return forums.stream().limit(50).collect(Collectors.toList());
	}

	@PostMapping(value = "/add-forum", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public void ForumResponse(@RequestBody ForumRequest forum, Model model) {
		service.addForum(forum, model);
	}

	@DeleteMapping(value = "/delete-forum")
	@ResponseStatus(HttpStatus.OK)
	public void ForumDeleteResponse(@RequestParam Long forumId, Model model) {
		service.deleteForum(forumId, model);
	}
}
