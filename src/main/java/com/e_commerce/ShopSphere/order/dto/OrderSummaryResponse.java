package com.e_commerce.ShopSphere.order.dto;

import com.e_commerce.ShopSphere.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderSummaryResponse {

    private Long id;

    private OrderStatus status;

    private BigDecimal totalAmount;

    private OffsetDateTime createdAt;
}