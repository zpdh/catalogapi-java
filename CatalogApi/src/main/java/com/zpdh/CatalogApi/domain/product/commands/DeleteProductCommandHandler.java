package com.zpdh.CatalogApi.domain.product.commands;

import com.zpdh.CatalogApi.domain.product.ProductErrors;
import com.zpdh.CatalogApi.domain.product.ProductRepository;
import com.zpdh.CatalogApi.shared.mediator.command.CommandHandler;
import com.zpdh.CatalogApi.shared.result.Result;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class DeleteProductCommandHandler implements CommandHandler<DeleteProductCommand, Result<Void>> {
    private final ProductRepository productRepository;

    public DeleteProductCommandHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public Result<Void> handle(DeleteProductCommand command) {
        if (!productRepository.existsById(command.id())) {
            return Result.failure(ProductErrors.NOT_FOUND);
        }

        productRepository.deleteById(command.id());
        return Result.success(null);
    }
}
