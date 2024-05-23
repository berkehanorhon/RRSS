package com.demo.rrss.rrssbackend.rest.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class RecommendationRequest {
    private Long productId;
    private Long userId;
    private Long categoryId;
    private Long ratingCount;
    private Float averageRating;
    private String imagePath;
    private String title;
    private String description;
}
