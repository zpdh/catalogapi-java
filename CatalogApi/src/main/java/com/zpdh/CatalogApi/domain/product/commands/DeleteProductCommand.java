package com.zpdh.CatalogApi.domain.product.commands;

import com.zpdh.CatalogApi.shared.mediator.command.Command;
import com.zpdh.CatalogApi.shared.result.Result;

public record DeleteProductCommand(Long id) implements Command<Result<Void>> {
}
