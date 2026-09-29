package com.e_commerce.ShopSphere.catalog.dto;


import com.e_commerce.ShopSphere.enums.ProductStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
public class ProductResponse {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private String sku;
    private BigDecimal price;
    private ProductStatus status;
    private Long categoryId;
    private String categoryName;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
