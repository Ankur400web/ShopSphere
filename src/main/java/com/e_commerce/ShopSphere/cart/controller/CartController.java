package com.e_commerce.ShopSphere.cart.controller;

import com.e_commerce.ShopSphere.cart.dto.AddToCartRequest;
import com.e_commerce.ShopSphere.cart.dto.CartResponse;
import com.e_commerce.ShopSphere.cart.dto.UpdateCartItemRequest;
import com.e_commerce.ShopSphere.cart.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/items")
    public ResponseEntity<CartResponse> addToCart(@Valid @RequestBody AddToCartRequest request){
        return ResponseEntity.ok(cartService.addToCart(request));
    }

    @GetMapping
    public ResponseEntity<CartResponse> getCart(){
        return ResponseEntity.ok(cartService.getCart());
    }

    @PutMapping("/items/{cartItemId}")
    public ResponseEntity<CartResponse> updateCartItem(
            @PathVariable Long cartItemId,
            @Valid @RequestBody UpdateCartItemRequest request) {

        return ResponseEntity.ok(
                cartService.updateCartItem(cartItemId, request)
        );
    }
}
