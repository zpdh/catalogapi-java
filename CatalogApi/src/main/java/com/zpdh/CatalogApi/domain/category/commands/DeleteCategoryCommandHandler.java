package com.zpdh.CatalogApi.domain.category.commands;

import com.zpdh.CatalogApi.domain.category.CategoryErrors;
import com.zpdh.CatalogApi.domain.category.CategoryRepository;
import com.zpdh.CatalogApi.shared.mediator.command.CommandHandler;
import com.zpdh.CatalogApi.shared.result.Result;
import jakarta.transaction.Transactional;

public class DeleteCategoryCommandHandler implements CommandHandler<DeleteCategoryCommand, Result<Void>> {

    private final CategoryRepository categoryRepository;

    public DeleteCategoryCommandHandler(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public Result<Void> handle(DeleteCategoryCommand command) {
        if (!categoryRepository.existsById(command.id())) {
            return Result.failure(CategoryErrors.NOT_FOUND);
        }

        return Result.success(null);

    }
}
