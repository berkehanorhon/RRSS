package com.rrss2024.RRSS.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.RestController;

import com.rrss2024.RRSS.service.ProductService;
import com.rrss2024.RRSS.dto.ProductDTO;
import com.rrss2024.RRSS.model.Product;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@RestController
public class ProductController {

    private final ProductService service;

    public ProductController (ProductService service) {
        this.service = service;
    }

    @GetMapping("/get-product")
	@ResponseStatus(HttpStatus.OK)
	public Product ProductResponse(@RequestParam Long productId) {
		return service.getProduct(productId);
	}

	@GetMapping("/get-all-products")
	@ResponseStatus(HttpStatus.OK)
	public List<Product> getAllProducts() {
		List<Product> products = service.getAllProducts();
		return products.stream().limit(10).collect(Collectors.toList());
	}

	@PostMapping(value = "/add-product", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public String ProductResponse(@RequestBody ProductDTO product) {
		service.addProduct(product);
        return "Product added successfully";
	}

	@PatchMapping(value = "/update-product")
	@ResponseStatus(HttpStatus.OK)
	public String ProductUpdateResponse(@RequestBody ProductDTO product) {
		service.updateProduct(product.getProductId(), product);
        return "Product updated successfully";
	}

	@DeleteMapping(value = "/delete-product")
	@ResponseStatus(HttpStatus.OK)
	public String ProductDeleteResponse(@RequestParam Long productId) {
		service.deleteProduct(productId);
        return "Product deleted successfully";
	}
    
}
