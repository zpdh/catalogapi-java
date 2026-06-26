package com.zpdh.CatalogApi.domain.product.dto;

import com.zpdh.CatalogApi.shared.messaging.payload.ProductEventPayload;

public record ProductDeletedPayload(Long id) implements ProductEventPayload {
}
