package com.e_commerce.ShopSphere.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    private int status;
    private String message;
    private OffsetDateTime timestamp;
}