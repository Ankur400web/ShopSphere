package com.e_commerce.ShopSphere.catalog.service;

import com.e_commerce.ShopSphere.catalog.dto.CreateInventoryRequest;
import com.e_commerce.ShopSphere.catalog.dto.InventoryResponse;
import com.e_commerce.ShopSphere.catalog.dto.UpdateInventoryRequest;
import com.e_commerce.ShopSphere.catalog.entity.Inventory;
import com.e_commerce.ShopSphere.catalog.entity.Product;
import com.e_commerce.ShopSphere.catalog.repository.InventoryRepository;
import com.e_commerce.ShopSphere.catalog.repository.ProductRepository;
import com.e_commerce.ShopSphere.common.exception.DuplicateInventoryException;
import com.e_commerce.ShopSphere.common.exception.InventoryNotFoundException;
import com.e_commerce.ShopSphere.common.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;


    @Transactional
    public InventoryResponse createInventory(CreateInventoryRequest request){

        if (inventoryRepository.existByProductId(request.getProductId())){
            throw new DuplicateInventoryException("Inventory already exist");
        }

        if (request.getQuantity()<0){
            throw new RuntimeException("Quantity cannot be negative");
        }

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ProductNotFoundException(
                        "Product not found with id: " + request.getProductId()
                ));

        Inventory inventory = new Inventory();

        inventory.setProduct(product);
        inventory.setQuantity(request.getQuantity());

        Inventory savedInventory = inventoryRepository.save(inventory);

        return mapToResponse(savedInventory);

    }

    private InventoryResponse mapToResponse(Inventory inventory){
        InventoryResponse response = new InventoryResponse();

        response.setId(inventory.getId());
        response.setProductId(inventory.getProduct().getId());
        response.setQuantity(inventory.getQuantity());
        response.setReservedQuantity(inventory.getReserved_quantity());
        response.setVersion(inventory.getVersion());
        response.setCreatedAt(inventory.getCreatedAt());
        response.setUpdatedAt(inventory.getUpdatedAt());

        return response;
    }

    public List<InventoryResponse> getAllInventory(){
        List<Inventory> inventories = inventoryRepository.findAll();

        return inventories.stream()
                .map(this::mapToResponse)
                .toList();
    }

    public  InventoryResponse getInventoryById(Long id){
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(()-> new InventoryNotFoundException("Inventory doesn't exist"));

        return mapToResponse(inventory);
    }

    @Transactional
    public InventoryResponse updateInventory(Long id, UpdateInventoryRequest request) {

        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new InventoryNotFoundException(
                        "Inventory not found with id: " + id
                ));

        if (request.getQuantity() < 0) {
            throw new IllegalArgumentException(
                    "Quantity cannot be negative"
            );
        }

        if (request.getQuantity() < inventory.getReserved_quantity()) {
            throw new IllegalArgumentException(
                    "Quantity cannot be less than reserved quantity: "
                            + inventory.getReserved_quantity()
            );
        }

        if (request.getVersion() != inventory.getVersion()) {
            throw new IllegalStateException(
                    "Inventory has been modified. Please fetch the latest inventory and try again."
            );
        }

        inventory.setQuantity(request.getQuantity());

        Inventory updatedInventory = inventoryRepository.save(inventory);

        return mapToResponse(updatedInventory);
    }



    public void deleteInventory(Long id){
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(()-> new InventoryNotFoundException("Inventory not found"));

        inventoryRepository.delete(inventory);
    }


}
