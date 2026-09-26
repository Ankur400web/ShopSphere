package com.e_commerce.ShopSphere.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.e_commerce.ShopSphere.catalog.entity.Category;

public interface CatalogRepository extends JpaRepository<Category, Long> {
}
