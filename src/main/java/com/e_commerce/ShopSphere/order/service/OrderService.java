package com.e_commerce.ShopSphere.order.service;

import com.e_commerce.ShopSphere.cart.entity.Cart;
import com.e_commerce.ShopSphere.cart.entity.CartItems;
import com.e_commerce.ShopSphere.cart.repository.CartItemsRepository;
import com.e_commerce.ShopSphere.cart.repository.CartRepository;
import com.e_commerce.ShopSphere.catalog.entity.Inventory;
import com.e_commerce.ShopSphere.catalog.entity.Product;
import com.e_commerce.ShopSphere.catalog.repository.InventoryRepository;
import com.e_commerce.ShopSphere.common.exception.CartNotFoundException;
import com.e_commerce.ShopSphere.common.exception.ProductNotFoundException;
import com.e_commerce.ShopSphere.enums.OrderStatus;
import com.e_commerce.ShopSphere.order.dto.CreateOrderRequest;
import com.e_commerce.ShopSphere.order.dto.OrderItemResponse;
import com.e_commerce.ShopSphere.order.dto.OrderResponse;
import com.e_commerce.ShopSphere.order.dto.OrderSummaryResponse;
import com.e_commerce.ShopSphere.order.entity.Order;
import com.e_commerce.ShopSphere.order.entity.OrderItem;
import com.e_commerce.ShopSphere.order.repository.OrderItemRepository;
import com.e_commerce.ShopSphere.order.repository.OrderRepository;
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

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    private final CartRepository cartRepository;
    private final CartItemsRepository cartItemsRepository;

    private final InventoryRepository inventoryRepository;
    private final UserRepository userRepository;


    /*
     * CREATE ORDER / CHECKOUT
     */
    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request) {

        User user = getAuthenticatedUser();

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new CartNotFoundException("Cart not found"));

        List<CartItems> cartItems =
                cartItemsRepository.findByCartId(cart.getId());

        if (cartItems.isEmpty()) {
            throw new IllegalArgumentException(
                    "Cannot create order from an empty cart"
            );
        }

        /*
         * Calculate subtotal from the current product prices.
         */
        BigDecimal subtotal = BigDecimal.ZERO;

        for (CartItems cartItem : cartItems) {

            Product product = cartItem.getProduct();

            BigDecimal itemSubtotal =
                    product.getPrice()
                            .multiply(
                                    BigDecimal.valueOf(
                                            cartItem.getQuantity()
                                    )
                            );

            subtotal = subtotal.add(itemSubtotal);
        }


        /*
         * Shipping is temporarily zero.
         *
         * We will replace this when the shipping/address
         * system is implemented.
         */
        BigDecimal shippingAmount = BigDecimal.ZERO;

        BigDecimal totalAmount =
                subtotal.add(shippingAmount);


        /*
         * Create Order
         */
        Order order = new Order();

        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        order.setSubtotal(subtotal);
        order.setShippingAmount(shippingAmount);
        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);


        /*
         * Convert CartItems → OrderItems
         */
        for (CartItems cartItem : cartItems) {

            Product product = cartItem.getProduct();

            int quantity = cartItem.getQuantity();

            BigDecimal unitPrice = product.getPrice();

            BigDecimal itemSubtotal =
                    unitPrice.multiply(
                            BigDecimal.valueOf(quantity)
                    );


            /*
             * Verify the inventory reservation.
             *
             * CartService already reserved the inventory
             * when the item was added to the cart.
             */
            Inventory inventory = inventoryRepository
                    .findByProductId(product.getId())
                    .orElseThrow(() ->
                            new ProductNotFoundException(
                                    "Inventory not found for product: "
                                            + product.getId()
                            )
                    );

            if (inventory.getReservedQuantity() < quantity) {

                throw new IllegalArgumentException(
                        "Insufficient reserved inventory for product: "
                                + product.getName()
                );
            }


            /*
             * Finalize the reservation.
             *
             * quantity was already reduced when the product
             * was added to the cart.
             *
             * Therefore we ONLY reduce reservedQuantity here.
             */
            inventory.setReservedQuantity(
                    inventory.getReservedQuantity() - quantity
            );

            inventoryRepository.save(inventory);


            /*
             * Create OrderItem
             */
            OrderItem orderItem = new OrderItem();

            orderItem.setOrder(savedOrder);
            orderItem.setProduct(product);
            orderItem.setQuantity(quantity);
            orderItem.setUnitPrice(unitPrice);
            orderItem.setSubtotal(itemSubtotal);

            orderItemRepository.save(orderItem);
        }


        /*
         * Clear cart items.
         *
         * IMPORTANT:
         *
         * We do NOT call cartService.emptyCart()
         *
         * because emptyCart() releases inventory.
         *
         * The inventory reservation has already been
         * finalized above.
         */
        cartItemsRepository.deleteAll(cartItems);


        /*
         * Return complete order response.
         */
        return buildOrderResponse(savedOrder);
    }


    /*
     * GET CURRENT USER'S ORDERS
     */
    @Transactional(readOnly = true)
    public List<OrderSummaryResponse> getMyOrders() {

        User user = getAuthenticatedUser();

        List<Order> orders =
                orderRepository.findByUserIdOrderByCreatedAtDesc(
                        user.getId()
                );

        List<OrderSummaryResponse> responses =
                new ArrayList<>();

        for (Order order : orders) {

            OrderSummaryResponse response =
                    new OrderSummaryResponse();

            response.setId(order.getId());
            response.setStatus(order.getStatus());
            response.setTotalAmount(order.getTotalAmount());
            response.setCreatedAt(order.getCreatedAt());

            responses.add(response);
        }

        return responses;
    }


    /*
     * GET ONE ORDER
     */
    @Transactional(readOnly = true)
    public OrderResponse getMyOrder(Long orderId) {

        User user = getAuthenticatedUser();

        Order order =
                orderRepository
                        .findByIdAndUserId(orderId, user.getId())
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Order not found"
                                ));

        return buildOrderResponse(order);
    }


    /*
     * BUILD ORDER RESPONSE
     */
    private OrderResponse buildOrderResponse(Order order) {

        List<OrderItem> orderItems =
                orderItemRepository.findByOrderId(order.getId());

        List<OrderItemResponse> itemResponses =
                new ArrayList<>();

        for (OrderItem orderItem : orderItems) {

            Product product = orderItem.getProduct();

            OrderItemResponse response =
                    new OrderItemResponse();

            response.setProductId(product.getId());
            response.setProductName(product.getName());
            response.setQuantity(orderItem.getQuantity());
            response.setUnitPrice(orderItem.getUnitPrice());
            response.setSubtotal(orderItem.getSubtotal());

            itemResponses.add(response);
        }


        OrderResponse response =
                new OrderResponse();

        response.setId(order.getId());
        response.setStatus(order.getStatus());
        response.setSubtotal(order.getSubtotal());
        response.setShippingAmount(order.getShippingAmount());
        response.setTotalAmount(order.getTotalAmount());
        response.setCreatedAt(order.getCreatedAt());
        response.setUpdatedAt(order.getUpdatedAt());
        response.setItems(itemResponses);

        return response;
    }


    /*
     * GET AUTHENTICATED USER
     */
    private User getAuthenticatedUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User doesn't exist"
                        ));
    }
}