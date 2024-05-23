package com.demo.rrss.rrssbackend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;

import com.demo.rrss.rrssbackend.entity.ForumPost;
import com.demo.rrss.rrssbackend.repository.ForumPostLikeRepository;
import com.demo.rrss.rrssbackend.repository.ForumPostRepository;
import com.demo.rrss.rrssbackend.repository.ForumReplyRepository;
import com.demo.rrss.rrssbackend.rest.request.ForumPostRequest;


@Service
public class ForumPostService {

    @Autowired
	ForumPostRepository repository;

	@Autowired 
	ForumReplyRepository forumReplyRepository;

	public ForumPost getForumPost(Long forumPostId) {
		Optional<ForumPost> response = repository.findById(forumPostId);
		return response.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));
	}

	public void addForumPost(ForumPostRequest request, Model model) {
		Long userId = (Long) model.getAttribute("userId"); 
		ForumPost forumPost = new ForumPost();
		forumPost.setPostData(request.getPostData());
		forumPost.setUserId(userId);
		forumPost.setPostName(request.getPostName());
		forumPost.setCreationDate(new java.sql.Timestamp(new java.util.Date().getTime()));
		forumPost.setForumId(request.getForumId());
		forumPost.setForumPostId(request.getForumPostId());
		repository.save(forumPost);
	}

	public void updateForumPost(Long forumPostId, ForumPostRequest request, Model model) {
		Optional<ForumPost> existingForumPost = repository.findById(forumPostId);
		Long userId = (Long) model.getAttribute("userId");
		if (existingForumPost.isPresent() && existingForumPost.get().getUserId() == userId){
			ForumPost forumPost = existingForumPost.get();
			forumPost.setPostData(request.getPostData());
			forumPost.setUserId(userId);
			forumPost.setPostName(request.getPostName());
			forumPost.setCreationDate(new java.sql.Timestamp(new java.util.Date().getTime()));
			forumPost.setForumId(request.getForumId());
			forumPost.setForumPostId(request.getForumPostId());
			repository.save(forumPost);
		} else {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Post not found or you do not have permission to update it.");
		}
	}

	public void deleteForumPost(Long forumPostId, Model model) {
		Long userId = (Long) model.getAttribute("userId");
		if (repository.existsById(forumPostId) && repository.findById(forumPostId).get().getUserId() == userId){
			forumReplyRepository.deleteByForumPostId(forumPostId);
			repository.deleteById(forumPostId);
		}	
		else
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Post not found or you do not have permission to delete it.");
	}

	public List<ForumPost> getAllForumPosts() {
		return (List<ForumPost>) repository.findAll();
	}

}
