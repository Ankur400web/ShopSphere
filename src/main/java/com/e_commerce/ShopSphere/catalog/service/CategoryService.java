package com.e_commerce.ShopSphere.catalog.service;


import com.e_commerce.ShopSphere.catalog.dto.CategoryResponse;
import com.e_commerce.ShopSphere.catalog.dto.CreateCategoryRequest;
import com.e_commerce.ShopSphere.catalog.entity.Category;
import com.e_commerce.ShopSphere.catalog.repository.CategoryRepository;
import com.e_commerce.ShopSphere.common.exception.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private CategoryResponse mapToResponse(Category category) {

        CategoryResponse response = new CategoryResponse();

        response.setId(category.getId());
        response.setName(category.getName());
        response.setDescription(category.getDescription());

        if (category.getParentCategory() != null) {
            response.setParentId(category.getParentCategory().getId());
        }

        response.setCreatedAt(category.getCreatedAt());
        response.setUpdatedAt(category.getUpdatedAt());

        return response;
    }

    @Transactional
    public CategoryResponse createCategory(CreateCategoryRequest request) {
        if(categoryRepository.existsByName(request.getName())){
            throw new IllegalArgumentException("Category with the same name already exists");
        }

        Category parentCategory = null;

        if (request.getParentId() != null) {
            parentCategory = categoryRepository.findById(request.getParentId())
                    .orElseThrow(() -> new CategoryNotFoundException(
                            "Parent category not found"
                    ));
        }

        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setParentCategory(parentCategory);

        Category savedCategory = categoryRepository.save(category);

        return mapToResponse(savedCategory);
    }

}
