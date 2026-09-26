package com.e_commerce.ShopSphere.common.exception;

public class SameEmailException extends RuntimeException {
    public SameEmailException(String message) {
        super(message);
    }
}
