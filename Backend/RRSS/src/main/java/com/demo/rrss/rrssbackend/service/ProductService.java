package com.demo.rrss.rrssbackend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.ui.Model;

import com.demo.rrss.rrssbackend.controller.UsersController;
import com.demo.rrss.rrssbackend.entity.Product;
import com.demo.rrss.rrssbackend.entity.Users;
import com.demo.rrss.rrssbackend.repository.ProductRepository;
import com.demo.rrss.rrssbackend.repository.UsersRepository;
import com.demo.rrss.rrssbackend.rest.request.ProductRequest;

@Service
public class ProductService {
	@Autowired
	ProductRepository repository;
	@Autowired
	UsersRepository uRepository;

	public Product getProduct(Long productId) {
		return repository.findById(productId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
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
			product.setPublishDate(new java.sql.Timestamp(new java.util.Date().getTime()));
			product.setTitle(request.getTitle());
			product.setUserId(request.getUserId());
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

	public List<Product> getAllProducts(Long categoryId) {
		if (categoryId == -1)
			return repository.findAllMax50();
		else {
			List<Product> products = repository.findProductsByCategoryId(categoryId);
			return products;
		}
	}

    public List<Product> getUsersAllProducts(Long userId) {
		return repository.findByUserId(userId);
	}
}