package com.demo.rrss.rrssbackend.repository;

import java.util.List;
import java.util.Set;

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

	@Query("SELECT p.productId FROM Product p WHERE p.categoryId IN :categoryIds")
	List<Long> findProductIdsByCategoryIds(@Param("categoryIds") Set<Long> categoryIds);

	@Query("SELECT p FROM Product p WHERE p.productId IN :productIds")
	List<Product> findAllByIdsSet(@Param("productIds") Set<Long> productIds);

	@Query("SELECT p FROM Product p WHERE p.productId IN :productIds")
	List<Product> findAllByIdsList(@Param("productIds") List<Long> productIds);



	List<Product> findByUserId(Long userId);
}
