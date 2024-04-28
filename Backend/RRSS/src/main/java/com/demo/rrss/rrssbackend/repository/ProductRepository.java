package com.demo.rrss.rrssbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.rrss.rrssbackend.entity.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
	@Query(nativeQuery = true, value = "SELECT * FROM Product p WHERE p.category_id = :categoryId ORDER BY p.publish_date DESC LIMIT 50")
	List<Product> findProductsByCategoryId(@Param("categoryId") Long categoryId);

	@Query(nativeQuery = true, value = "SELECT * FROM Product p ORDER BY p.publish_date DESC LIMIT 50")
	List<Product> findAllMax50();
}
