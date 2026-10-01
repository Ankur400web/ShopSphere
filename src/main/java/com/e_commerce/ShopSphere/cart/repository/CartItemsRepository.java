package com.e_commerce.ShopSphere.cart.repository;

import com.e_commerce.ShopSphere.cart.entity.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemsRepository extends JpaRepository<CartItems, Long> {

    Optional<CartItems> findByCartIdAndProductId(Long cartId, Long productId);

    List<CartItems> findByCartId(Long cartId);
}
