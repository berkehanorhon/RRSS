package com.rrss2024.RRSS.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.rrss2024.RRSS.dto.ProductDTO;
import com.rrss2024.RRSS.model.Product;
import com.rrss2024.RRSS.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product getProduct(Long productId) {
		Optional<Product> response = repository.findById(productId);
		System.out.println(response);
		return response.get();
		}

	public void addProduct(ProductDTO request) {
		Product product = new Product();
		product.setCategoryId(request.getCategoryId());
		String dateString = request.getPublishDate().toString();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			java.util.Date utilDate = format.parse(dateString);
			java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
			LocalDate publishDate = sqlDate.toLocalDate(); // Convert java.sql.Date to java.time.LocalDate
			product.setPublishDate(publishDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		product.setTitle(request.getTitle());
		product.setUserId(request.getUserId());
		product.setDescription(request.getDescription());
		repository.save(product);
	}

	public void updateProduct(Long productId, ProductDTO request) {
		Optional<Product> existingProduct = repository.findById(productId);
		if (existingProduct.isPresent()) {
			Product product = existingProduct.get();
			product.setCategoryId(request.getCategoryId());
			product.setDescription(request.getDescription());

			String dateString = request.getPublishDate().toString();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			try {
				java.util.Date utilDate = format.parse(dateString);
				java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                LocalDate publishDate = sqlDate.toLocalDate();
				product.setPublishDate(publishDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
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

	public List<Product> getAllProducts() {
		return (List<Product>) repository.findAll();
	}
}
