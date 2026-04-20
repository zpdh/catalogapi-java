package com.zpdh.CatalogApi.domain.product;

import com.zpdh.CatalogApi.domain.category.Category;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    protected Product() {
    }

    private Product(String name, String description, BigDecimal price, Integer stock, Category category) {

        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category = category;

    }

    public static Product create(String name, String description, BigDecimal price, Integer stock, Category category) {
        return new Product(name, description, price, stock, category);
    }

    public void update(String name, String description, BigDecimal price, Integer stock, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getStock() {
        return stock;
    }

    public Category getCategory() {
        return category;
    }
}
