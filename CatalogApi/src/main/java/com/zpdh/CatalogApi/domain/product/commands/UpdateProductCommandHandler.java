package com.zpdh.CatalogApi.domain.product.commands;

import com.zpdh.CatalogApi.domain.category.CategoryErrors;
import com.zpdh.CatalogApi.domain.category.CategoryRepository;
import com.zpdh.CatalogApi.domain.product.ProductErrors;
import com.zpdh.CatalogApi.domain.product.ProductRepository;
import com.zpdh.CatalogApi.domain.product.dto.ProductResponse;
import com.zpdh.CatalogApi.shared.mediator.command.CommandHandler;
import com.zpdh.CatalogApi.shared.messaging.EventPublisher;
import com.zpdh.CatalogApi.shared.result.Result;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class UpdateProductCommandHandler implements CommandHandler<UpdateProductCommand, Result<ProductResponse>> {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final EventPublisher eventPublisher;

    public UpdateProductCommandHandler(ProductRepository productRepository, CategoryRepository categoryRepository, EventPublisher eventPublisher) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    @Transactional
    public Result<ProductResponse> handle(UpdateProductCommand command) {
        return productRepository.findById(command.id())
            .map(product -> categoryRepository.findById(command.request().categoryId())
                .map(cat -> {
                    product.update(
                        command.request().name(),
                        command.request().description(),
                        command.request().price(),
                        command.request().stock(),
                        cat
                    );
                    eventPublisher.publish("product.updated", cat.toDto());


                    return Result.success(product.toDto());
                }).orElse(Result.failure(CategoryErrors.NOT_FOUND))
            ).orElse(Result.failure(ProductErrors.NOT_FOUND));

    }
}
