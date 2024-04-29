package com.rai.demo.repository;

import com.rai.demo.controller.ProductController;
import com.rai.demo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
