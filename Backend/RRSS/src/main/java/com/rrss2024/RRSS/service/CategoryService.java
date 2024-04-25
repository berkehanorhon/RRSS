package com.rrss2024.RRSS.service;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.rrss2024.RRSS.model.Category;
import com.rrss2024.RRSS.repository.CategoryRepository;

@Service
public class CategoryService {
    private final CategoryRepository repository;
    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }
    public Category getCategory(Long categoryId) {
		Optional<Category> response = repository.findById(categoryId);
		System.out.println(response);
		return response.get();
	}

	public Category addCategory(Category category) {
        return repository.save(category);
    }

    public Category updateCategory(Long categoryId, Category categoryDetails) {
        if (!repository.existsById(categoryId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
        }
        Category category = getCategory(categoryId);
        category.setCategoryName(categoryDetails.getCategoryName());
        category.setDescription(categoryDetails.getDescription());
        return repository.save(category);
    }

    public String deleteCategory(Long categoryId) {
		if (repository.existsById(categoryId)) {
			repository.deleteById(categoryId);
            return "Category deleted successfully";
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
		}
	}
}
