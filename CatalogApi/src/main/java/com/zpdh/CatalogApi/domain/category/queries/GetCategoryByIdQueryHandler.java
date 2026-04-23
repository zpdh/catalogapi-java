package com.zpdh.CatalogApi.domain.category.queries;

import com.zpdh.CatalogApi.domain.category.CategoryErrors;
import com.zpdh.CatalogApi.domain.category.CategoryRepository;
import com.zpdh.CatalogApi.domain.category.dtos.CategoryResponse;
import com.zpdh.CatalogApi.shared.mediator.query.QueryHandler;
import com.zpdh.CatalogApi.shared.result.Result;

public class GetCategoryByIdQueryHandler implements QueryHandler<GetCategoryByIdQuery, Result<CategoryResponse>> {

    private final CategoryRepository categoryRepository;

    public GetCategoryByIdQueryHandler(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Result<CategoryResponse> handle(GetCategoryByIdQuery query) {
        return categoryRepository.findById(query.id())
            .map(cat -> Result.success(cat.toDto()))
            .orElse(Result.failure(CategoryErrors.NOT_FOUND));
    }
}
