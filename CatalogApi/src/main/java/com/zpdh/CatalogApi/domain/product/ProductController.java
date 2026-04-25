package com.zpdh.CatalogApi.domain.product;

import com.zpdh.CatalogApi.domain.product.commands.CreateProductCommand;
import com.zpdh.CatalogApi.domain.product.commands.DeleteProductCommand;
import com.zpdh.CatalogApi.domain.product.commands.UpdateProductCommand;
import com.zpdh.CatalogApi.domain.product.dto.CreateProductRequest;
import com.zpdh.CatalogApi.domain.product.dto.ProductResponse;
import com.zpdh.CatalogApi.domain.product.dto.UpdateProductRequest;
import com.zpdh.CatalogApi.domain.product.queries.GetAllProductsQuery;
import com.zpdh.CatalogApi.domain.product.queries.GetProductByIdQuery;
import com.zpdh.CatalogApi.shared.mediator.Mediator;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final Mediator mediator;

    public ProductController(Mediator mediator) {
        this.mediator = mediator;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAll() {
        List<ProductResponse> response = mediator.query(new GetAllProductsQuery()).getOrThrow();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getById(@PathVariable Long id) {
        ProductResponse response = mediator.query(new GetProductByIdQuery(id)).getOrThrow();

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody CreateProductRequest request) {
        ProductResponse response = mediator.send(new CreateProductCommand(request)).getOrThrow();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> put(@PathVariable Long id, @Valid @RequestBody UpdateProductRequest request) {
        ProductResponse response = mediator.send(new UpdateProductCommand(id, request)).getOrThrow();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        mediator.send(new DeleteProductCommand(id)).getOrThrow();

        return ResponseEntity.noContent().build();
    }
}
