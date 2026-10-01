package com.e_commerce.ShopSphere.catalog.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateInventoryRequest {


    @NotNull
    @PositiveOrZero
    private int quantity;

    @NotNull
    private int version;
}
