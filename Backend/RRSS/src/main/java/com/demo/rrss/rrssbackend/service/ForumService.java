package com.demo.rrss.rrssbackend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;

import com.demo.rrss.rrssbackend.entity.Forum;
import com.demo.rrss.rrssbackend.repository.ForumRepository;
import com.demo.rrss.rrssbackend.repository.UsersRepository;
import com.demo.rrss.rrssbackend.rest.request.ForumRequest;


@Service
public class ForumService {
    @Autowired
	ForumRepository repository;
	@Autowired
	UsersRepository userRepository;
	public Forum getForum(Long forumId) {
		Optional<Forum> response = repository.findById(forumId);
		return response.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Forum not found"));
	}

	public void addForum(ForumRequest request, Model model) {
		Long userId = (Long) model.getAttribute("userId");
		if(!userRepository.findById(userId).get().getIsAdmin()) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You do not have permission to do that!");
		}
		Forum forum = new Forum();
		forum.setForumName(request.getForumName());
		forum.setForumDescription(request.getForumDescription());
		forum.setCreationDate(new java.sql.Timestamp(new java.util.Date().getTime()));
		repository.save(forum);
	}

	public void updateForum(Long forumId, ForumRequest request, Model model) {
		Long userId = (Long) model.getAttribute("userId");
		if(!userRepository.findById(userId).get().getIsAdmin()) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You do not have permission to do that!");
		}
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

	public void deleteForum(Long forumId, Model model) {
		Long userId = (Long) model.getAttribute("userId");
		if(!userRepository.findById(userId).get().getIsAdmin()) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You do not have permission to do that!");
		}
		if (repository.existsById(forumId))
			repository.deleteById(forumId);
		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Forum not found");
    }

	public List<Forum> getAllForums(Model model) {
		Long userId = (Long) model.getAttribute("userId");
		if(!userRepository.findById(userId).get().getIsAdmin()) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You do not have permission to do that!");
		}

		return (List<Forum>) repository.findAll();
	}

}
