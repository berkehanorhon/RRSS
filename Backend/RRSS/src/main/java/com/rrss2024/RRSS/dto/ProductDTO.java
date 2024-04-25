package com.rrss2024.RRSS.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data

public class ProductDTO {
    @NotBlank(message = "Product title cannot be blank")
    private String title;

    @NotBlank(message = "Product description cannot be blank")
    private String description;

    @NotBlank(message = "Product id cannot be blank")
    private Long productId;

    @NotBlank(message = "Product user id cannot be blank")
    private Long userId;

    @NotBlank(message = "Product category id cannot be blank")
    private Long categoryId;

    @NotBlank(message = "Product date cannot be blank")
    private LocalDate publishDate = LocalDate.now();
}
