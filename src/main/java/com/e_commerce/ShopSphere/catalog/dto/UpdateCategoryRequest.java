package com.e_commerce.ShopSphere.catalog.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCategoryRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    private Long parentId;
}
