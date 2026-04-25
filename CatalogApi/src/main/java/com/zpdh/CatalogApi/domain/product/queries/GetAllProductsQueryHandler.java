package com.zpdh.CatalogApi.domain.product.queries;

import com.zpdh.CatalogApi.domain.product.Product;
import com.zpdh.CatalogApi.domain.product.ProductRepository;
import com.zpdh.CatalogApi.domain.product.dto.ProductResponse;
import com.zpdh.CatalogApi.shared.mediator.query.QueryHandler;
import com.zpdh.CatalogApi.shared.result.Result;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetAllProductsQueryHandler implements QueryHandler<GetAllProductsQuery, Result<List<ProductResponse>>> {
    private final ProductRepository productRepository;

    public GetAllProductsQueryHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Result<List<ProductResponse>> handle(GetAllProductsQuery query) {
        List<ProductResponse> products = productRepository.findAll()
            .stream()
            .map(Product::toDto)
            .toList();

        return Result.success(products);
    }
}
