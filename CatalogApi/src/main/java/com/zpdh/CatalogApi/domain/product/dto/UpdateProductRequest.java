package com.zpdh.CatalogApi.domain.product.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record UpdateProductRequest(
    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must be at most 100 characters")
    String name,
    @Size(max = 255, message = "Description must be at most 255 characters")
    String description,
    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    BigDecimal price,
    @NotNull(message = "Stock is required")
    @PositiveOrZero(message = "Stock must be non negative")
    Integer stock,
    @NotNull(message = "Category is required")
    Long categoryId
) {
}
