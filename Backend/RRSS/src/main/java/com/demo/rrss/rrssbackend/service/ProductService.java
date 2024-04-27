package com.demo.rrss.rrssbackend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.demo.rrss.rrssbackend.entity.Product;
import com.demo.rrss.rrssbackend.repository.ProductRepository;
import com.demo.rrss.rrssbackend.rest.request.ProductRequest;

@Service
public class ProductService {
	@Autowired
	ProductRepository repository;

	public Product getProduct(Long productId) {
		return repository.findById(productId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
	}

	public void addProduct(ProductRequest request) {
		Product product = new Product();
		product.setCategoryId(request.getCategoryId());
		product.setDescription(request.getDescription());
		product.setPublishDate(new java.sql.Timestamp(new java.util.Date().getTime()));

		product.setTitle(request.getTitle());
		product.setUserId(request.getUserId());
		repository.save(product);
	}

	public void updateProduct(Long productId, ProductRequest request) {
		Optional<Product> existingProduct = repository.findById(productId);
		if (existingProduct.isPresent()) {
			Product product = existingProduct.get();
			product.setCategoryId(request.getCategoryId());
			product.setDescription(request.getDescription());
			product.setPublishDate(new java.sql.Timestamp(new java.util.Date().getTime()));
			product.setTitle(request.getTitle());
			product.setUserId(request.getUserId());
			repository.save(product);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
		}
	}

	public void deleteProduct(Long productId) {
		if (repository.existsById(productId))
			repository.deleteById(productId);
		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");

	}

	public List<Product> getAllProducts(Long categoryId) {
		if (categoryId == -1)
			return (List<Product>) repository.findAll();
		else {
			List<Product> products = repository.findProductsByCategoryId(categoryId);
			return products;
		}
	}
}