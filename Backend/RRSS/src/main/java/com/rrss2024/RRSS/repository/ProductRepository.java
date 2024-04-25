package com.rrss2024.RRSS.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rrss2024.RRSS.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
    Optional<Product> findById(Long id);
}
