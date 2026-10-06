package com.e_commerce.ShopSphere.order.dto;

import com.e_commerce.ShopSphere.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private Long id;

    private OrderStatus status;

    private BigDecimal subtotal;

    private BigDecimal shippingAmount;

    private BigDecimal totalAmount;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    private List<OrderItemResponse> items;
}