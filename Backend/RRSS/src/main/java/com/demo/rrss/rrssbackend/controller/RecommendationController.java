package com.demo.rrss.rrssbackend.controller;

import com.demo.rrss.rrssbackend.rest.request.RecommendationRequest;
import com.demo.rrss.rrssbackend.service.JwtUtil;
import com.demo.rrss.rrssbackend.service.RecommendationService;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@RestController
@RequestMapping("/recommendation")

public class RecommendationController {
    private final RecommendationService recommendationService;
    private final JwtUtil jwtUtil;

    public RecommendationController(RecommendationService recommendationService, JwtUtil jwtUtil) {
        this.recommendationService = recommendationService;
        this.jwtUtil = jwtUtil;
    }

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

    @GetMapping("/get")
    public Set<RecommendationRequest> recommendProducts(Model model) {
        Long userId = (Long) model.getAttribute("userId");
        return recommendationService.getRecommendation(userId);
    }

}
