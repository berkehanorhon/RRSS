package com.demo.rrss.rrssbackend.controller;

import com.demo.rrss.rrssbackend.entity.Product;
import com.demo.rrss.rrssbackend.service.JwtUtil;
import com.demo.rrss.rrssbackend.service.RecommendationService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @ModelAttribute // TODO Herhangi bir hata durumunda 403 döndürülecek
    public void addUserIdToModel(@RequestHeader(value="Authorization") String bearerToken, Model model) {
        String token = bearerToken.substring(7);
        Long userId = jwtUtil.extractUserId(token);
        model.addAttribute("userId", userId);
    }

    @GetMapping("/get")
    public Set<Product> recommendProducts(Model model) {
        Long userId = (Long) model.getAttribute("userId");
        return recommendationService.getRecommendation(userId);
    }

}
