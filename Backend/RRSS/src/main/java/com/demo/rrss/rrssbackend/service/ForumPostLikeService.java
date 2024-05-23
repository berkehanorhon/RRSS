package com.demo.rrss.rrssbackend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;

import com.demo.rrss.rrssbackend.entity.ForumPostLike;
import com.demo.rrss.rrssbackend.repository.ForumPostLikeRepository;
import com.demo.rrss.rrssbackend.rest.request.ForumPostLikeRequest;

import com.demo.rrss.rrssbackend.repository.ForumPostRepository;


@Service
public class ForumPostLikeService {

    @Autowired
	ForumPostLikeRepository repository;

	@Autowired
	ForumPostRepository forumPostRepository;

	public Integer getForumPostLikeNumber(ForumPostLikeRequest request){
		if(forumPostRepository.existsById(request.getForumPostId())){
			return repository.getCountByForumPostId(request.getForumPostId());
		}
		else 
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forum Post not found.");
	}

	public void addForumPostLike(ForumPostLikeRequest request, Model model) {
		Long userId = (Long) model.getAttribute("userId"); // Not used at the moment
		ForumPostLike forumPostLike = new ForumPostLike();
		forumPostLike.setForumPostId(request.getForumPostId());
        forumPostLike.setUserId(userId);
        forumPostLike.setLikeDate(new java.sql.Timestamp(new java.util.Date().getTime()));
		repository.save(forumPostLike);
	}

	
	//public void deleteForumPostLike(Long forumPostId, Model model) {
	//	Long userId = (Long) model.getAttribute("userId");
	//	if (repository.existsByForumPostId(forumPostId) && repository.findByForumPostId(forumPostId).getUserId() == userId)
	//		repository.deleteByForumPostId(forumPostId);
	//	else
	//		throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Like not found or you do not have permission to delete it.");
	//}
	
	
}
