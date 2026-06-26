package com.zpdh.CatalogApi.domain.category.dtos;

import com.zpdh.CatalogApi.shared.messaging.payload.CategoryEventPayload;

public record CategoryDeletedPayload(Long id) implements CategoryEventPayload {
}
