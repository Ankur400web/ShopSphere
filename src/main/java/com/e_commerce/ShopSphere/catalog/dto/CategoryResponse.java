package com.e_commerce.ShopSphere.catalog.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class CategoryResponse {

    private Long id;
    private String name;
    private String description;
    private Long parentId;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
