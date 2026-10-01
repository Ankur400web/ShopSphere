package com.e_commerce.ShopSphere.cart.service;


import com.e_commerce.ShopSphere.cart.dto.AddToCartRequest;
import com.e_commerce.ShopSphere.cart.dto.CartItemResponse;
import com.e_commerce.ShopSphere.cart.dto.CartResponse;
import com.e_commerce.ShopSphere.cart.dto.UpdateCartItemRequest;
import com.e_commerce.ShopSphere.cart.entity.Cart;
import com.e_commerce.ShopSphere.cart.entity.CartItems;
import com.e_commerce.ShopSphere.cart.repository.CartItemsRepository;
import com.e_commerce.ShopSphere.cart.repository.CartRepository;
import com.e_commerce.ShopSphere.catalog.entity.Inventory;
import com.e_commerce.ShopSphere.catalog.entity.Product;
import com.e_commerce.ShopSphere.catalog.repository.InventoryRepository;
import com.e_commerce.ShopSphere.catalog.repository.ProductRepository;
import com.e_commerce.ShopSphere.common.exception.CartNotFoundException;
import com.e_commerce.ShopSphere.common.exception.ProductNotFoundException;
import com.e_commerce.ShopSphere.user.entity.User;
import com.e_commerce.ShopSphere.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemsRepository cartItemsRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;

    @Transactional
    public CartResponse addToCart(AddToCartRequest request) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User doesn't exist"));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found"));

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });

        Inventory inventory = inventoryRepository
                .findByProductId(product.getId())
                .orElseThrow(() ->
                        new ProductNotFoundException("Product is not available"));

        if (inventory.getQuantity() < request.getQuantity()) {
            throw new IllegalArgumentException(
                    "Insufficient inventory for this product"
            );
        }

        Optional<CartItems> existingItem =
                cartItemsRepository.findByCartIdAndProductId(
                        cart.getId(),
                        product.getId()
                );

        CartItems cartItem;

        if (existingItem.isPresent()) {

            cartItem = existingItem.get();

            int newQuantity =
                    cartItem.getQuantity() + request.getQuantity();

            cartItem.setQuantity(newQuantity);

        } else {

            cartItem = new CartItems();

            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
        }

        inventory.setQuantity(
                inventory.getQuantity() - request.getQuantity()
        );

        inventory.setReservedQuantity(
                inventory.getReservedQuantity() + request.getQuantity()
        );

        cartItemsRepository.save(cartItem);
        inventoryRepository.save(inventory);

        return buildCartResponse(cart);
    }

    private CartResponse buildCartResponse(Cart cart) {

        List<CartItems> cartItems =
                cartItemsRepository.findByCartId(cart.getId());

        List<CartItemResponse> itemResponses = new ArrayList<>();

        BigDecimal totalAmount = BigDecimal.ZERO;
        int totalItems = 0;

        for (CartItems item : cartItems) {

            Product product = item.getProduct();

            BigDecimal unitPrice = product.getPrice();

            BigDecimal subtotal =
                    unitPrice.multiply(
                            BigDecimal.valueOf(item.getQuantity())
                    );

            CartItemResponse itemResponse = new CartItemResponse();

            itemResponse.setId(item.getId());
            itemResponse.setProductId(product.getId());
            itemResponse.setProductName(product.getName());
            itemResponse.setQuantity(item.getQuantity());
            itemResponse.setUnitPrice(unitPrice);
            itemResponse.setSubtotal(subtotal);

            itemResponses.add(itemResponse);

            totalAmount = totalAmount.add(subtotal);
            totalItems += item.getQuantity();
        }

        CartResponse response = new CartResponse();

        response.setCartId(cart.getId());
        response.setUserId(cart.getUser().getId());
        response.setItems(itemResponses);
        response.setTotalAmount(totalAmount);
        response.setTotalItems(totalItems);

        return response;
    }

    public CartResponse getCart(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("User doesn't exist"));

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(()-> new CartNotFoundException("Cart not found"));

        return buildCartResponse(cart);
    }


    @Transactional
    public CartResponse updateCartItem(
            Long cartItemId,
            UpdateCartItemRequest request) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User doesn't exist"));

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Cart not found"));

        CartItems cartItem = cartItemsRepository
                .findByIdAndCartId(cartItemId, cart.getId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Cart item not found"));

        Inventory inventory = inventoryRepository
                .findByProductId(cartItem.getProduct().getId())
                .orElseThrow(() ->
                        new ProductNotFoundException("Inventory not found"));

        int oldQuantity = cartItem.getQuantity();
        int newQuantity = request.getQuantity();

        int difference = newQuantity - oldQuantity;

        if (difference > 0) {

            if (inventory.getQuantity() < difference) {
                throw new IllegalArgumentException(
                        "Insufficient inventory for this product"
                );
            }

            inventory.setQuantity(
                    inventory.getQuantity() - difference
            );

            inventory.setReservedQuantity(
                    inventory.getReservedQuantity() + difference
            );

        } else if (difference < 0) {

            int releasedQuantity = Math.abs(difference);

            inventory.setQuantity(
                    inventory.getQuantity() + releasedQuantity
            );

            inventory.setReservedQuantity(
                    inventory.getReservedQuantity() - releasedQuantity
            );
        }

        cartItem.setQuantity(newQuantity);

        inventoryRepository.save(inventory);
        cartItemsRepository.save(cartItem);

        return buildCartResponse(cart);
    }
}
