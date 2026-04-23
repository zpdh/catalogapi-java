package com.zpdh.CatalogApi.domain.category.commands;

import com.zpdh.CatalogApi.domain.category.CategoryErrors;
import com.zpdh.CatalogApi.domain.category.CategoryRepository;
import com.zpdh.CatalogApi.domain.category.dtos.CategoryResponse;
import com.zpdh.CatalogApi.domain.category.dtos.CreateCategoryRequest;
import com.zpdh.CatalogApi.domain.category.dtos.UpdateCategoryRequest;
import com.zpdh.CatalogApi.shared.mediator.command.CommandHandler;
import com.zpdh.CatalogApi.shared.result.Result;
import jakarta.transaction.Transactional;

public class UpdateCategoryCommandHandler implements CommandHandler<UpdateCategoryCommand, Result<CategoryResponse>> {

    private final CategoryRepository categoryRepository;

    public UpdateCategoryCommandHandler(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public Result<CategoryResponse> handle(UpdateCategoryCommand command) {
        return categoryRepository
            .findById(command.id())
            .map(cat -> {
                cat.update(
                    command.request().name(),
                    command.request().description()
                );

                return Result.success(cat.toDto());
            }).orElse(Result.failure(CategoryErrors.NOT_FOUND));
    }
}
