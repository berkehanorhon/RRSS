package com.demo.rrss.rrssbackend.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.demo.rrss.rrssbackend.entity.Product;
import com.demo.rrss.rrssbackend.rest.request.ProductRequest;
import com.demo.rrss.rrssbackend.service.JwtUtil;
import com.demo.rrss.rrssbackend.service.ProductService;

@RestController
public class ProductController {
	@Autowired
	ProductService service;

	@Autowired
	private JwtUtil jwtUtil;

	@ModelAttribute // TODO Herhangi bir hata durumunda 403 döndürülecek
	public void addUserIdToModel(@RequestHeader(value="Authorization") String bearerToken, Model model) {
		String token = bearerToken.substring(7);
		Long userId = jwtUtil.extractUserId(token);
		model.addAttribute("userId", userId);
	}

	@GetMapping("/get-product")
	@ResponseStatus(HttpStatus.OK)
	public HashMap<String, Object> ProductResponse(@RequestParam Long productId) {
		return service.getProduct(productId);
	}

	@GetMapping("/get-users-products")
	@ResponseStatus(HttpStatus.OK)
	public List<Product> getUsersAllProducts(@RequestParam Long userId) {
        return service.getUsersAllProducts(userId);
	}

	@GetMapping("/get-all-products")
	@ResponseStatus(HttpStatus.OK)
	public HashSet getAllProducts(@RequestParam Long categoryId) {
		return service.getAllProducts(categoryId);
	}

	@PostMapping(value = "/add-product", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public void ProductResponse(@RequestBody ProductRequest product, Model model) {
		service.addProduct(product, model);
	}

	@PatchMapping(value = "/update-product")
	@ResponseStatus(HttpStatus.OK)
	public void ProductUpdateResponse(@RequestBody ProductRequest product, Model model) {
		service.updateProduct(product.getProductId(), product, model);
	}

	@DeleteMapping(value = "/delete-product")
	@ResponseStatus(HttpStatus.OK)
	public void ProductDeleteResponse(@RequestParam Long productId, Model model) {
		service.deleteProduct(productId, model);
	}
}
