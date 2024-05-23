package com.demo.rrss.rrssbackend.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.demo.rrss.rrssbackend.entity.Forum;
import com.demo.rrss.rrssbackend.rest.request.ForumRequest;
import com.demo.rrss.rrssbackend.service.ForumService;

@RestController
public class ForumController {

    @Autowired
	ForumService service;

	@GetMapping("/get-forum")
	@ResponseStatus(HttpStatus.OK)
	public Forum ForumResponse(@RequestParam Long forumId) {
		return service.getForum(forumId);
	}

	@GetMapping("/get-all-forums")
	@ResponseStatus(HttpStatus.OK)
	public List<Forum> getAllForums() {
		List<Forum> forums = service.getAllForums();
		return forums.stream().limit(50).collect(Collectors.toList());
	}

	@PostMapping(value = "/add-forum", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public void ForumResponse(@RequestBody ForumRequest forum) {
		service.addForum(forum);
	}

	@PatchMapping(value = "/update-forum")
	@ResponseStatus(HttpStatus.OK)
	public void ForumUpdateResponse(@RequestBody ForumRequest forum) {
		service.updateForum(forum.getForumId(), forum);
	}

	@DeleteMapping(value = "/delete-forum")
	@ResponseStatus(HttpStatus.OK)
	public void ForumDeleteResponse(@RequestParam Long forumId) {
		service.deleteForum(forumId);
	}
}
