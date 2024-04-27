package com.demo.rrss.rrssbackend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.demo.rrss.rrssbackend.entity.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

}
