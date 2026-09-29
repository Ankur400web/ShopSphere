package com.e_commerce.ShopSphere.auth.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {

    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private Long roleId;
    private String email;
    private String role;
    private String firstName;
}
