package com.rrss2024.RRSS.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rrss2024.RRSS.model.Category;
import com.rrss2024.RRSS.service.CategoryService;

@RestController
public class CategoryController {

    private final CategoryService service;
    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping("/get-category")
	@ResponseStatus(HttpStatus.OK)
	public Category CategoryResponse(@RequestParam Long categoryId) {
		return service.getCategory(categoryId);
	}

	@PostMapping(value = "/add-category", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public String CategoryResponse(@RequestBody Category category) {
		service.addCategory(category);
        return "Category added successfully";
	}

	@PatchMapping(value = "/update-category")
	@ResponseStatus(HttpStatus.OK)
	public String CategoryUpdateResponse(@RequestBody Category category) {
		service.updateCategory(category.getCategoryId(), category);
        return "Category updated successfully";
	}

	@DeleteMapping(value = "/delete-category")
	@ResponseStatus(HttpStatus.OK)
	public String CategoryDeleteResponse(@RequestParam Long categoryId) {
		service.deleteCategory(categoryId);
        return "Category deleted successfully";
	}
}
