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

import com.demo.rrss.rrssbackend.service.ForumReplyService;
import com.demo.rrss.rrssbackend.service.JwtUtil;
import com.demo.rrss.rrssbackend.entity.ForumReply;
import com.demo.rrss.rrssbackend.rest.request.ForumReplyRequest;

@RestController
@RequestMapping("/forum")
public class ForumReplyController {

    @Autowired
	private JwtUtil jwtUtil;

	@Autowired
	ForumReplyService service;

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

	@GetMapping("/get-reply")
	@ResponseStatus(HttpStatus.OK)
	public ForumReply getForumReply(@RequestParam Long forumReplyId) {
		return service.getForumReply(forumReplyId);
	}

	@GetMapping("/get-all-replies")
	@ResponseStatus(HttpStatus.OK)
	public List<ForumReply> getAllForumReplies() {
		List<ForumReply> forumReplies = service.getAllForumReplies();
		return forumReplies.stream().limit(10).collect(Collectors.toList());
	}

	@PostMapping(value = "/add-reply", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public void addForumReply(@RequestBody ForumReplyRequest forumReply, Model model) {
		service.addForumReply(forumReply,model);
	}


	@DeleteMapping(value = "/delete-reply")
	@ResponseStatus(HttpStatus.OK)
	public void deleteForumReply(@RequestParam Long forumReplyId, Model model) {
		service.deleteForumReply(forumReplyId, model);
	}
}
