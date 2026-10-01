package com.e_commerce.ShopSphere.catalog.controller;


import com.e_commerce.ShopSphere.catalog.dto.CreateInventoryRequest;
import com.e_commerce.ShopSphere.catalog.dto.InventoryResponse;
import com.e_commerce.ShopSphere.catalog.dto.UpdateInventoryRequest;
import com.e_commerce.ShopSphere.catalog.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<InventoryResponse> createInventory(@Valid @RequestBody CreateInventoryRequest request){
        InventoryResponse response = inventoryService.createInventory(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<InventoryResponse>> getAllInventories(){
        List<InventoryResponse> inventoryResponses = inventoryService.getAllInventory();

        return ResponseEntity.ok().body(inventoryResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryResponse> getInventoryById(@PathVariable Long id){
        InventoryResponse response = inventoryService.getInventoryById(id);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryResponse> updateInventory(@PathVariable Long id,
                                                             @Valid @RequestBody UpdateInventoryRequest request){
        InventoryResponse response = inventoryService.updateInventory(id, request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long id){
        inventoryService.deleteInventory(id);

        return ResponseEntity.noContent().build();
    }
}
