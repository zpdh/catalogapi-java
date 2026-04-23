package com.zpdh.CatalogApi.domain.category.queries;

import com.zpdh.CatalogApi.domain.category.dtos.CategoryResponse;
import com.zpdh.CatalogApi.shared.mediator.query.Query;
import com.zpdh.CatalogApi.shared.result.Result;

public record GetCategoryByIdQuery(Long id) implements Query<Result<CategoryResponse>> {
}
