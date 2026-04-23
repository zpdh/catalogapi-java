package com.zpdh.CatalogApi.domain.category.commands;

import com.zpdh.CatalogApi.shared.mediator.command.Command;
import com.zpdh.CatalogApi.shared.result.Result;

public record DeleteCategoryCommand(Long id) implements Command<Result<Void>> {
}
