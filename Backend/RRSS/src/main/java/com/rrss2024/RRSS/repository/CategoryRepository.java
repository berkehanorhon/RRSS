package com.rrss2024.RRSS.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rrss2024.RRSS.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
    Optional<Category> findById(Long id);
}
