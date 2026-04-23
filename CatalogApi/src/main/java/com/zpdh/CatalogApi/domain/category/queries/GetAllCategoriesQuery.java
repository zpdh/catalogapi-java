package com.zpdh.CatalogApi.domain.category.queries;

import com.zpdh.CatalogApi.domain.category.dtos.CategoryResponse;
import com.zpdh.CatalogApi.shared.mediator.query.Query;

import java.util.List;

public record GetAllCategoriesQuery() implements Query<List<CategoryResponse>> {
}
