package com.zpdh.CatalogApi.domain.product.commands;

import com.zpdh.CatalogApi.domain.category.CategoryErrors;
import com.zpdh.CatalogApi.domain.category.CategoryRepository;
import com.zpdh.CatalogApi.domain.product.Product;
import com.zpdh.CatalogApi.domain.product.ProductRepository;
import com.zpdh.CatalogApi.domain.product.dto.ProductResponse;
import com.zpdh.CatalogApi.shared.mediator.command.CommandHandler;
import com.zpdh.CatalogApi.shared.messaging.EventPublisher;
import com.zpdh.CatalogApi.shared.result.Result;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class CreateProductCommandHandler implements CommandHandler<CreateProductCommand, Result<ProductResponse>> {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final EventPublisher eventPublisher;

    public CreateProductCommandHandler(
        ProductRepository productRepository,
        CategoryRepository categoryRepository,
        EventPublisher eventPublisher) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    @Transactional
    public Result<ProductResponse> handle(CreateProductCommand command) {
        return categoryRepository.findById(command.request().categoryId())
            .map(cat -> {
                Product product = Product.create(
                    command.request().name(),
                    command.request().description(),
                    command.request().price(),
                    command.request().stock(),
                    cat);
                productRepository.save(product);
                eventPublisher.publish("product.created", product.toDto());

                return Result.success(product.toDto());
            }).orElse(Result.failure(CategoryErrors.NOT_FOUND));
    }
}
