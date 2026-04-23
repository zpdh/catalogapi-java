package com.zpdh.CatalogApi.domain.category.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCategoryRequest(
    @NotBlank(message = "name is required")
    @Size(max = 100, message = "Name must be 100 characters at most")
    String name,
    @Size(max = 255, message = "Description must be at most 255 characters")
    String description) {
}
