package com.e_commerce.ShopSphere.catalog.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateInventoryRequest {

    @NotNull
    private Long productId;

    @NotNull
    @Min(0)
    @PositiveOrZero
    private Integer quantity;
}
