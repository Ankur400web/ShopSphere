package com.e_commerce.ShopSphere.address.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponse {

    private Long id;

    private String fullName;

    private String street;

    private String city;

    private String state;

    private String postalCode;

    private String country;

    private String phoneNumber;

    private boolean defaultAddress;
}