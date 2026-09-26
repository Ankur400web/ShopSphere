package com.e_commerce.ShopSphere.inventory.repository;

import com.e_commerce.ShopSphere.inventory.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
