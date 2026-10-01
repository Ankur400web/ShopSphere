package com.e_commerce.ShopSphere.common.exception;

public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException(String cartNotFound) {
        super(cartNotFound);
    }
}
