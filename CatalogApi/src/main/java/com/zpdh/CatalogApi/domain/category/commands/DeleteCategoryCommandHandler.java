package com.zpdh.CatalogApi.domain.category.commands;

import com.zpdh.CatalogApi.domain.category.CategoryErrors;
import com.zpdh.CatalogApi.domain.category.CategoryRepository;
import com.zpdh.CatalogApi.domain.category.dtos.CategoryDeletedPayload;
import com.zpdh.CatalogApi.shared.mediator.command.CommandHandler;
import com.zpdh.CatalogApi.shared.messaging.EventPublisher;
import com.zpdh.CatalogApi.shared.result.Result;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class DeleteCategoryCommandHandler implements CommandHandler<DeleteCategoryCommand, Result<Void>> {

    private final CategoryRepository categoryRepository;
    private final EventPublisher eventPublisher;

    public DeleteCategoryCommandHandler(CategoryRepository categoryRepository, EventPublisher eventPublisher) {
        this.categoryRepository = categoryRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    @Transactional
    public Result<Void> handle(DeleteCategoryCommand command) {
        if (!categoryRepository.existsById(command.id())) {
            return Result.failure(CategoryErrors.NOT_FOUND);
        }

        categoryRepository.deleteById(command.id());
        eventPublisher.publish("category.delete", new CategoryDeletedPayload(command.id()));

        return Result.success(null);

    }
}
