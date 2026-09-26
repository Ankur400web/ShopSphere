package com.e_commerce.ShopSphere.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.e_commerce.ShopSphere.catalog.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
