package com.e_commerce.ShopSphere.catalog.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCategoryRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    private Long parentId;
}
