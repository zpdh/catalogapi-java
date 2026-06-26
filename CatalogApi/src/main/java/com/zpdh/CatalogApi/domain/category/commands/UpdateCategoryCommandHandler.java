package com.zpdh.CatalogApi.domain.category.commands;

import com.zpdh.CatalogApi.domain.category.CategoryErrors;
import com.zpdh.CatalogApi.domain.category.CategoryRepository;
import com.zpdh.CatalogApi.domain.category.dtos.CategoryResponse;
import com.zpdh.CatalogApi.shared.mediator.command.CommandHandler;
import com.zpdh.CatalogApi.shared.messaging.EventPublisher;
import com.zpdh.CatalogApi.shared.result.Result;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class UpdateCategoryCommandHandler implements CommandHandler<UpdateCategoryCommand, Result<CategoryResponse>> {

    private final CategoryRepository categoryRepository;
    private final EventPublisher eventPublisher;

    public UpdateCategoryCommandHandler(CategoryRepository categoryRepository, EventPublisher eventPublisher) {
        this.categoryRepository = categoryRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    @Transactional
    public Result<CategoryResponse> handle(UpdateCategoryCommand command) {
        return categoryRepository.findById(command.id())
            .map(cat -> {
                cat.update(
                    command.request().name(),
                    command.request().description()
                );
                eventPublisher.publish("category.updated", cat.toDto());

                return Result.success(cat.toDto());
            }).orElse(Result.failure(CategoryErrors.NOT_FOUND));
    }
}
