package com.zpdh.CatalogApi.domain.product.dto;

import com.zpdh.CatalogApi.shared.messaging.payload.ProductEventPayload;

import java.math.BigDecimal;

public record ProductResponse(
    Long id,
    String name,
    String description,
    BigDecimal price,
    Integer stock,
    Long categoryId,
    String categoryName) implements ProductEventPayload {
}
