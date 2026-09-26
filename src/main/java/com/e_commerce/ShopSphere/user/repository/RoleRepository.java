package com.e_commerce.ShopSphere.user.repository;

import com.e_commerce.ShopSphere.user.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
