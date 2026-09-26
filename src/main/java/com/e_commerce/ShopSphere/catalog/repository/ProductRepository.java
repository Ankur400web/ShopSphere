package com.e_commerce.ShopSphere.catalog.repository;

import com.e_commerce.ShopSphere.catalog.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
