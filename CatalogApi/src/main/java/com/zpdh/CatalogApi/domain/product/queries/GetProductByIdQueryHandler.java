package com.zpdh.CatalogApi.domain.product.queries;

import com.zpdh.CatalogApi.domain.product.ProductErrors;
import com.zpdh.CatalogApi.domain.product.ProductRepository;
import com.zpdh.CatalogApi.domain.product.dto.ProductResponse;
import com.zpdh.CatalogApi.shared.mediator.query.QueryHandler;
import com.zpdh.CatalogApi.shared.result.Result;
import org.springframework.stereotype.Component;

@Component
public class GetProductByIdQueryHandler implements QueryHandler<GetProductByIdQuery, Result<ProductResponse>> {
    private final ProductRepository productRepository;

    public GetProductByIdQueryHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Result<ProductResponse> handle(GetProductByIdQuery query) {
        return productRepository.findById(query.id())
            .map(product -> Result.success(product.toDto())
            ).orElse(Result.failure(ProductErrors.NOT_FOUND));
    }
}
