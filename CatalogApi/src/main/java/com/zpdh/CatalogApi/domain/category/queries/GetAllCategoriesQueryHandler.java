package com.zpdh.CatalogApi.domain.category.queries;

import com.zpdh.CatalogApi.domain.category.Category;
import com.zpdh.CatalogApi.domain.category.CategoryRepository;
import com.zpdh.CatalogApi.domain.category.dtos.CategoryResponse;
import com.zpdh.CatalogApi.shared.mediator.query.QueryHandler;

import java.util.List;

public class GetAllCategoriesQueryHandler implements QueryHandler<GetAllCategoriesQuery, List<CategoryResponse>> {
    private final CategoryRepository categoryRepository;

    public GetAllCategoriesQueryHandler(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryResponse> handle(GetAllCategoriesQuery query) {
        return categoryRepository.findAll()
            .stream()
            .map(Category::toDto) // equivalent to cat -> cat.toDto()
            .toList();
    }
}
