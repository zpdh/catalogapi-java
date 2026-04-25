package com.zpdh.CatalogApi.domain.product.queries;

import com.zpdh.CatalogApi.domain.product.dto.ProductResponse;
import com.zpdh.CatalogApi.shared.mediator.query.Query;
import com.zpdh.CatalogApi.shared.result.Result;

import java.util.List;

public record GetAllProductsQuery() implements Query<Result<List<ProductResponse>>> {
}
