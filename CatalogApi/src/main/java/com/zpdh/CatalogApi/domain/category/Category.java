package com.zpdh.CatalogApi.domain.category;

import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column
    private String description;

    protected Category() {
    }

    private Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public static Category create(String name, String description) {
        return new Category(name, description);
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
}
