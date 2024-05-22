package com.demo.rrss.rrssbackend.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.aspectj.internal.lang.annotation.ajcDeclareAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.ui.Model;

import com.demo.rrss.rrssbackend.controller.UsersController;
import com.demo.rrss.rrssbackend.entity.Product;
import com.demo.rrss.rrssbackend.entity.Users;
import com.demo.rrss.rrssbackend.repository.ProductRatingRepository;
import com.demo.rrss.rrssbackend.repository.ProductRepository;
import com.demo.rrss.rrssbackend.repository.UsersRepository;
import com.demo.rrss.rrssbackend.rest.request.ProductRequest;

@Service
public class ProductService {
	@Autowired
	ProductRepository repository;
	@Autowired
	UsersRepository uRepository;
	@Autowired
	ProductRatingRepository prRepository;
	public HashMap<String, Object> getProduct(Long productId) {
		Product product = repository.findById(productId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
	
		HashMap<String, Object> productMap = new HashMap<>();
		productMap.put("productId", product.getProductId());
		productMap.put("title", product.getTitle());
		productMap.put("description", product.getDescription());
		productMap.put("publishDate", product.getPublishDate());
		productMap.put("userId", product.getUserId());
		productMap.put("categoryId", product.getCategoryId());
		productMap.put("imagePath", product.getImagePath());
		productMap.put("averageRating", prRepository.getAverageRatingOrZero(product.getProductId()));
		productMap.put("ratingCount", prRepository.findRatingCountByProductId(product.getProductId()));
		return productMap;
	}

	public void addProduct(ProductRequest request, Model model) {
		Long userId = (Long) model.getAttribute("userId"); 
		Users user = uRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
		if (!user.getIsMerchant())
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not a merchant");
		Product product = new Product();
		product.setCategoryId(request.getCategoryId());
		product.setDescription(request.getDescription());
		product.setPublishDate(new java.sql.Timestamp(new java.util.Date().getTime()));

		product.setTitle(request.getTitle());
		product.setUserId(userId);
		repository.save(product);
	}

	public void updateProduct(Long productId, ProductRequest request, Model model) {
		Long userId = (Long) model.getAttribute("userId");
		Optional<Product> existingProduct = repository.findById(productId);
		if (existingProduct.isPresent() && existingProduct.get().getUserId() == userId){
			Product product = existingProduct.get();
			product.setCategoryId(request.getCategoryId());
			product.setDescription(request.getDescription());
			product.setPublishDate(new java.sql.Timestamp(new java.util.Date().getTime())); // TODO kalsın mı
			product.setTitle(request.getTitle());
			repository.save(product);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
		}
	}

	public void deleteProduct(Long productId, Model model) {
		Long userId = (Long) model.getAttribute("userId");
		if (repository.existsById(productId) && repository.findById(productId).get().getUserId() == userId)
			repository.deleteById(productId);
		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");

	}

	public HashSet getAllProducts(Long categoryId) {
		List<Product> products;
		HashSet response = new HashSet<>();
		if (categoryId == -1) {
			products = repository.findAllMax50();
		} else {
			products = repository.findProductsByCategoryId(categoryId);
		}
		for (Product product : products) {
			HashMap<String, Object> productMap = new HashMap<>();
			productMap.put("productId", product.getProductId());
			productMap.put("title", product.getTitle());
			productMap.put("description", product.getDescription());
			productMap.put("publishDate", product.getPublishDate());
			productMap.put("userId", product.getUserId());
			productMap.put("categoryId", product.getCategoryId());
			productMap.put("imagePath", product.getImagePath());
			productMap.put("averageRating", prRepository.getAverageRatingOrZero(product.getProductId()));
			productMap.put("ratingCount", prRepository.findRatingCountByProductId(product.getProductId()));
			response.add(productMap);
		}
		return response;
	}

	public List<Product> getUsersAllProducts(Long userId) {
		return repository.findByUserId(userId);
	}
}