package com.e_commerce.ShopSphere.address.controller;

import com.e_commerce.ShopSphere.address.dto.AddressResponse;
import com.e_commerce.ShopSphere.address.dto.CreateAddressRequest;
import com.e_commerce.ShopSphere.address.dto.UpdateAddressRequest;
import com.e_commerce.ShopSphere.address.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;


    @PostMapping
    public ResponseEntity<AddressResponse> createAddress(
            @Valid @RequestBody CreateAddressRequest request
    ) {

        AddressResponse response =
                addressService.createAddress(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    @GetMapping
    public ResponseEntity<List<AddressResponse>> getMyAddresses() {

        return ResponseEntity.ok(
                addressService.getMyAddresses()
        );
    }


    @GetMapping("/{addressId}")
    public ResponseEntity<AddressResponse> getMyAddress(
            @PathVariable Long addressId
    ) {

        return ResponseEntity.ok(
                addressService.getMyAddress(addressId)
        );
    }


    @PutMapping("/{addressId}")
    public ResponseEntity<AddressResponse> updateAddress(
            @PathVariable Long addressId,
            @Valid @RequestBody UpdateAddressRequest request
    ) {

        return ResponseEntity.ok(
                addressService.updateAddress(
                        addressId,
                        request
                )
        );
    }


    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddress(
            @PathVariable Long addressId
    ) {

        addressService.deleteAddress(addressId);

        return ResponseEntity.noContent().build();
    }


    @PatchMapping("/{addressId}/default")
    public ResponseEntity<AddressResponse> setDefaultAddress(
            @PathVariable Long addressId
    ) {

        return ResponseEntity.ok(
                addressService.setDefaultAddress(addressId)
        );
    }
}