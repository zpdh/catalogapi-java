package com.zpdh.CatalogApi.domain.category;

import com.zpdh.CatalogApi.domain.category.commands.CreateCategoryCommand;
import com.zpdh.CatalogApi.domain.category.commands.DeleteCategoryCommand;
import com.zpdh.CatalogApi.domain.category.commands.UpdateCategoryCommand;
import com.zpdh.CatalogApi.domain.category.dtos.CategoryResponse;
import com.zpdh.CatalogApi.domain.category.dtos.CreateCategoryRequest;
import com.zpdh.CatalogApi.domain.category.dtos.UpdateCategoryRequest;
import com.zpdh.CatalogApi.domain.category.queries.GetAllCategoriesQuery;
import com.zpdh.CatalogApi.domain.category.queries.GetCategoryByIdQuery;
import com.zpdh.CatalogApi.shared.mediator.Mediator;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final Mediator mediator;

    public CategoryController(Mediator mediator) {
        this.mediator = mediator;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAll() {
        List<CategoryResponse> response = mediator.query(new GetAllCategoriesQuery());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getById(@PathVariable Long id) {
        CategoryResponse response = mediator.query(new GetCategoryByIdQuery(id)).getOrThrow();

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> create(@Valid @RequestBody CreateCategoryRequest request) {
        CategoryResponse response = mediator.send(new CreateCategoryCommand(request)).getOrThrow();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(@PathVariable Long id, @Valid @RequestBody UpdateCategoryRequest request) {
        CategoryResponse response = mediator.send(new UpdateCategoryCommand(id, request)).getOrThrow();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        mediator.send(new DeleteCategoryCommand(id)).getOrThrow();

        return ResponseEntity.noContent().build();
    }
}
