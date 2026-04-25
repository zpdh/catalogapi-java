package com.zpdh.CatalogApi.domain.product.commands;

import com.zpdh.CatalogApi.domain.product.dto.ProductResponse;
import com.zpdh.CatalogApi.domain.product.dto.UpdateProductRequest;
import com.zpdh.CatalogApi.shared.mediator.command.Command;
import com.zpdh.CatalogApi.shared.result.Result;

public record UpdateProductCommand(Long id, UpdateProductRequest request) implements Command<Result<ProductResponse>> {
}
