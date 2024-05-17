package com.demo.rrss.rrssbackend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.demo.rrss.rrssbackend.entity.Category;
import com.demo.rrss.rrssbackend.repository.CategoryRepository;

@Service
public class CategoryService {
	@Autowired
	CategoryRepository repository;

	public Category getCategory(Long categoryId) {
		Optional<Category> response = repository.findById(categoryId);
		System.out.println(response);
		return response.get();
	}

	public Category addCategory(Category category) { // TODO yetki kontrolü eklenecek
		return repository.save(category);
	}

	public Category updateCategory(Long categoryId, Category categoryDetails) { // TODO yetki kontrolü eklenecek
		Category category = getCategory(categoryId);
		category.setCategoryName(categoryDetails.getCategoryName());
		category.setDescription(categoryDetails.getDescription());
		return repository.save(category);
	}

	public void deleteCategory(Long categoryId) { // TODO yetki kontrolü eklenecek
		if (repository.existsById(categoryId)) {
			repository.deleteById(categoryId);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
		}
	}

	public List<Category> getAllCategories() {
		return (List<Category>) repository.findAll();
	}
}