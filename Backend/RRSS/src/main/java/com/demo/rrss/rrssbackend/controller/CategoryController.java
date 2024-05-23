package com.demo.rrss.rrssbackend.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.demo.rrss.rrssbackend.entity.Category;
import com.demo.rrss.rrssbackend.rest.request.AddCategoryRequest;
import com.demo.rrss.rrssbackend.service.CategoryService;

@RestController
public class CategoryController {
	@Autowired
	CategoryService service;

	@GetMapping("/get-category")
	@ResponseStatus(HttpStatus.OK)
	public Category CategoryResponse(@RequestParam Long categoryId) {
		return service.getCategory(categoryId);
	}

	@GetMapping("/get-all-categories")
	@ResponseStatus(HttpStatus.OK)
	public List<Category> getAllProducts() {
		List<Category> products = service.getAllCategories();
		return products.stream().limit(50).collect(Collectors.toList());
	}

	@PostMapping(value = "/add-category", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public void CategoryResponse(@RequestBody AddCategoryRequest category) {
		service.addCategory(category);
	}

	@PatchMapping(value = "/update-category")
	@ResponseStatus(HttpStatus.OK)
	public void CategoryUpdateResponse(@RequestBody Category category) {
		service.updateCategory(category.getCategoryId(), category);
	}

	@DeleteMapping(value = "/delete-category")
	@ResponseStatus(HttpStatus.OK)
	public void CategoryDeleteResponse(@RequestParam Long categoryId) {
		service.deleteCategory(categoryId);
	}
}
