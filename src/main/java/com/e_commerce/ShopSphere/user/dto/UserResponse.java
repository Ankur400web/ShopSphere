package com.e_commerce.ShopSphere.user.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String roleName;
    private String status;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
