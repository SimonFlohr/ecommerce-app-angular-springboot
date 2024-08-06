package com.simonf.ecommerce.dao;

import com.simonf.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

// Endpoint for product repository is automatically configured by Spring Data REST
public interface ProductRepository extends JpaRepository<Product, Long> {
}