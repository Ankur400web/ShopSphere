package com.e_commerce.ShopSphere.catalog.service;

import com.e_commerce.ShopSphere.catalog.dto.CreateProductRequest;
import com.e_commerce.ShopSphere.catalog.dto.ProductResponse;
import com.e_commerce.ShopSphere.catalog.dto.UpdateProductRequest;
import com.e_commerce.ShopSphere.catalog.entity.Category;
import com.e_commerce.ShopSphere.catalog.entity.Product;
import com.e_commerce.ShopSphere.catalog.repository.CategoryRepository;
import com.e_commerce.ShopSphere.catalog.repository.ProductRepository;
import com.e_commerce.ShopSphere.common.exception.CategoryNotFoundException;
import com.e_commerce.ShopSphere.common.exception.DuplicateSkuException;
import com.e_commerce.ShopSphere.common.exception.DuplicateSlugException;
import com.e_commerce.ShopSphere.common.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public ProductResponse createProduct(CreateProductRequest request){

        if (productRepository.existsBySku(request.getSku())){
            throw new DuplicateSkuException("Product with SKU already exists: " + request.getSku());
        }
        if (productRepository.existsBySlug(request.getSlug())){
            throw new DuplicateSlugException("Product with slug already exists: " + request.getSlug());
        }

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(
                        "Category not found with id: " + request.getCategoryId()
                ));

        Product product = new Product();
        product.setName(request.getName());
        product.setSlug(request.getSlug());
        product.setDescription(request.getDescription());
        product.setSku(request.getSku());
        product.setPrice(request.getPrice());
        product.setStatus(request.getStatus());
        product.setCategory(category);

        Product savedProduct = productRepository.save(product);

        return mapToResponse(savedProduct);


    }

    private ProductResponse mapToResponse(Product product) {

        ProductResponse response = new ProductResponse();

        response.setId(product.getId());
        response.setName(product.getName());
        response.setSlug(product.getSlug());
        response.setDescription(product.getDescription());
        response.setSku(product.getSku());
        response.setPrice(product.getPrice());
        response.setStatus(product.getStatus());
        response.setCategoryId(product.getCategory().getId());
        response.setCategoryName(product.getCategory().getName());

        response.setCreatedAt(product.getCreatedAt());
        response.setUpdatedAt(product.getUpdatedAt());

        return response;
    }

    public List<ProductResponse> getAllProducts() {

        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(this::mapToResponse)
                .toList();
    }

    public ProductResponse getProductById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(
                        "Product not found with id: " + id
                ));

        return mapToResponse(product);
    }


    @Transactional
    public ProductResponse updateProduct(Long id, UpdateProductRequest request) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(
                        "Product not found with id: " + id
                ));

        if (productRepository.existsBySkuAndIdNot(request.getSku(), id)) {
            throw new DuplicateSkuException(
                    "Product with SKU already exists: " + request.getSku()
            );
        }

        if (productRepository.existsBySlugAndIdNot(request.getSlug(), id)) {
            throw new DuplicateSlugException(
                    "Product with slug already exists: " + request.getSlug()
            );
        }

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(
                        "Category not found with id: " + request.getCategoryId()
                ));

        product.setName(request.getName());
        product.setSlug(request.getSlug());
        product.setDescription(request.getDescription());
        product.setSku(request.getSku());
        product.setPrice(request.getPrice());
        product.setStatus(request.getStatus());
        product.setCategory(category);

        Product updatedProduct = productRepository.save(product);

        return mapToResponse(updatedProduct);
    }

    @Transactional
    public void deleteProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(
                        "Product not found with id: " + id
                ));

        productRepository.delete(product);
    }
}
