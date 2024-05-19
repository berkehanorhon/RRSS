package com.demo.rrss.rrssbackend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.ui.Model;
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

	public void addProduct(ProductRequest request, Model model) { // TODO yetki kontrolü eklenecek
		Long userId = (Long) model.getAttribute("userId"); 
		Product product = new Product();
		product.setCategoryId(request.getCategoryId());
		product.setDescription(request.getDescription());
		product.setPublishDate(new java.sql.Timestamp(new java.util.Date().getTime()));

		product.setTitle(request.getTitle());
		product.setUserId(userId);
		repository.save(product);
	}

	public void updateProduct(Long productId, ProductRequest request) { // TODO yetki kontrolü eklenecek
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

	public void deleteProduct(Long productId) { // TODO yetki kontrolü eklenecek
		if (repository.existsById(productId))
			repository.deleteById(productId);
		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");

	}

	public List<Product> getAllProducts(Long categoryId) { // TODO yetki kontrolü eklenecek
		if (categoryId == -1)
			return repository.findAllMax50();
		else {
			List<Product> products = repository.findProductsByCategoryId(categoryId);
			return products;
		}
	}
}