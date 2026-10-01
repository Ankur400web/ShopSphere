package com.e_commerce.ShopSphere.cart.dto;

import lombok.Setter;

import java.math.BigDecimal;

@Setter
public class CartItemResponse {

    private Long id;
    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;

}
