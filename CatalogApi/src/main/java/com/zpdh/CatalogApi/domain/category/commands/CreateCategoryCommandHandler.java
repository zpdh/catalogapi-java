package com.zpdh.CatalogApi.domain.category.commands;

import com.zpdh.CatalogApi.domain.category.Category;
import com.zpdh.CatalogApi.domain.category.CategoryErrors;
import com.zpdh.CatalogApi.domain.category.CategoryRepository;
import com.zpdh.CatalogApi.domain.category.dtos.CategoryResponse;
import com.zpdh.CatalogApi.shared.mediator.command.CommandHandler;
import com.zpdh.CatalogApi.shared.result.Result;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class CreateCategoryCommandHandler implements CommandHandler<CreateCategoryCommand, Result<CategoryResponse>> {
    private final CategoryRepository categoryRepository;

    public CreateCategoryCommandHandler(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public Result<CategoryResponse> handle(CreateCategoryCommand command) {
        if (categoryRepository.existsByName(command.request().name())) {
            return Result.failure(CategoryErrors.ALREADY_EXISTS);
        }

        Category category = Category.create(
            command.request().name(),
            command.request().description()
        );

        categoryRepository.save(category);

        return Result.success(category.toDto());
    }
}
