package com.zpdh.CatalogApi.domain.category.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateCategoryRequest(
    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must be at most 100 characters")
    String name,
    @Size(max = 255, message = "Description must be at most 255 characters")
    String description) {
}
