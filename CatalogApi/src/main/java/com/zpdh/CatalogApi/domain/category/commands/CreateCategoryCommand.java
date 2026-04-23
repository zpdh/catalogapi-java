package com.zpdh.CatalogApi.domain.category.commands;

import com.zpdh.CatalogApi.domain.category.dtos.CategoryResponse;
import com.zpdh.CatalogApi.domain.category.dtos.CreateCategoryRequest;
import com.zpdh.CatalogApi.shared.mediator.command.Command;
import com.zpdh.CatalogApi.shared.result.Result;

public record CreateCategoryCommand(CreateCategoryRequest request) implements Command<Result<CategoryResponse>> {
}
