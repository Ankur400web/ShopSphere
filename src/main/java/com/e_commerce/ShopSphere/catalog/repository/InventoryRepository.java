package com.e_commerce.ShopSphere.catalog.repository;

import com.e_commerce.ShopSphere.catalog.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findByProductId(Long id);

    boolean existsByProductId(Long productId);

    void deleteByProductId(Long id);
}
