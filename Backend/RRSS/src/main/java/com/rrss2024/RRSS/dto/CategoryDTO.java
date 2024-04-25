package com.rrss2024.RRSS.dto;

import jakarta.validation.constraints.NotBlank;

public class CategoryDTO {
    @NotBlank(message = "Category id cannot be blank")
    private Long categoryId;

    @NotBlank(message = "Category name cannot be blank")
    private String categoryName;

    @NotBlank(message = "Category description cannot be blank")
    private String description = "No description available";
}
