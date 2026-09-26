package com.e_commerce.ShopSphere.user.repository;

import com.e_commerce.ShopSphere.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);
}
