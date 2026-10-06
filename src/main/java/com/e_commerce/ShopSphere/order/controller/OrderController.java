package com.e_commerce.ShopSphere.order.controller;

import com.e_commerce.ShopSphere.order.dto.CreateOrderRequest;
import com.e_commerce.ShopSphere.order.dto.OrderResponse;
import com.e_commerce.ShopSphere.order.dto.OrderSummaryResponse;
import com.e_commerce.ShopSphere.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;


    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @Valid @RequestBody CreateOrderRequest request
    ) {

        OrderResponse response =
                orderService.createOrder(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    @GetMapping
    public ResponseEntity<List<OrderSummaryResponse>> getMyOrders() {

        List<OrderSummaryResponse> response =
                orderService.getMyOrders();

        return ResponseEntity.ok(response);
    }


    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getMyOrder(
            @PathVariable Long orderId
    ) {

        OrderResponse response =
                orderService.getMyOrder(orderId);

        return ResponseEntity.ok(response);
    }
}