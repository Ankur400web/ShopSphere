package com.e_commerce.ShopSphere.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.xml.catalog.Catalog;

public interface CatalogRepository extends JpaRepository<Catalog, Long> {
}
