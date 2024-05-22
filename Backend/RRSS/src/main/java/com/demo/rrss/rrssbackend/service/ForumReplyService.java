package com.demo.rrss.rrssbackend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;

import com.demo.rrss.rrssbackend.entity.ForumReply;
import com.demo.rrss.rrssbackend.repository.ForumReplyRepository;
import com.demo.rrss.rrssbackend.rest.request.ForumReplyRequest;

@Service
public class ForumReplyService {
    @Autowired
	ForumReplyRepository repository;

	public ForumReply getForumReply(Long forumReplyId) {
		Optional<ForumReply> response = repository.findById(forumReplyId);
		return response.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reply not found"));
	}

	public void addForumReply(ForumReplyRequest request, Model model) {
		Long userId = (Long) model.getAttribute("userId"); 
		ForumReply forumReply = new ForumReply();
		forumReply.setForumPostId(request.getForumPostId());
        forumReply.setForumReplyId(request.getForumReplyId());
        forumReply.setPostData(request.getPostData());
        forumReply.setUserId(userId);
		forumReply.setCreationDate(new java.sql.Timestamp(new java.util.Date().getTime()));
		repository.save(forumReply);
	}

	public void updateForumReply(Long forumReplyId, ForumReplyRequest request, Model model) {
		Optional<ForumReply> existingForumReply = repository.findById(forumReplyId);
		Long userId = (Long) model.getAttribute("userId");
		if (existingForumReply.isPresent() && existingForumReply.get().getUserId() == userId){
			ForumReply forumReply = existingForumReply.get();
			forumReply.setPostData(request.getPostData());
			forumReply.setCreationDate(new java.sql.Timestamp(new java.util.Date().getTime()));
			repository.save(forumReply);
		} else {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Reply not found or you do not have permission to update it.");
		}
	}

	public void deleteForumReply(Long forumReplyId, Model model) {
		Long userId = (Long) model.getAttribute("userId");
		if (repository.existsById(forumReplyId) && repository.findById(forumReplyId).get().getUserId() == userId)
			repository.deleteById(forumReplyId);
		else
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Reply not found or you do not have permission to delete it.");
	}

	public List<ForumReply> getAllForumReplies() {
		return (List<ForumReply>) repository.findAll();
	}

}
