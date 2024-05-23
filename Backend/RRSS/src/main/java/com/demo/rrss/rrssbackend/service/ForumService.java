package com.demo.rrss.rrssbackend.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;

import com.demo.rrss.rrssbackend.entity.Forum;
import com.demo.rrss.rrssbackend.repository.ForumRepository;
import com.demo.rrss.rrssbackend.rest.request.ForumRequest;


@Service
public class ForumService {
    @Autowired
	ForumRepository repository;

	public Forum getForum(Long forumId) {
		Optional<Forum> response = repository.findById(forumId);
		return response.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Forum not found"));
	}

	public void addForum(ForumRequest request) {
		Forum forum = new Forum();
		forum.setForumName(request.getForumName());
		forum.setForumDescription(request.getForumDescription());
		forum.setCreationDate(new java.sql.Timestamp(new java.util.Date().getTime()));
		repository.save(forum);
	}

	public void updateForum(Long forumId, ForumRequest request) {
		Optional<Forum> existingForum = repository.findById(forumId);
		if (existingForum.isPresent()){
			Forum forum = existingForum.get();
			forum.setForumDescription(request.getForumDescription());
            forum.setForumName(request.getForumName());
			forum.setCreationDate(new java.sql.Timestamp(new java.util.Date().getTime()));
			repository.save(forum);
		} else {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forum not found");
		}
	}

	public void deleteForum(Long forumId) {
		if (repository.existsById(forumId))
			repository.deleteById(forumId);
		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Forum not found");
    }

	public List<Forum> getAllForums() {
		return (List<Forum>) repository.findAll();
	}

}
