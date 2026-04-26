package com.zpdh.CatalogApi.shared.config;

import com.zpdh.CatalogApi.domain.category.Category;
import com.zpdh.CatalogApi.domain.category.CategoryRepository;
import com.zpdh.CatalogApi.domain.product.Product;
import com.zpdh.CatalogApi.domain.product.ProductRepository;
import com.zpdh.CatalogApi.domain.user.Role;
import com.zpdh.CatalogApi.domain.user.User;
import com.zpdh.CatalogApi.domain.user.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Popula nossa DB com dados iniciais na primeira execução.
 * Garante que um usuário ADMIN, categorias e produtos sempre existam para testes da aplicação.
 */
@Component
public class DataSeeder implements ApplicationRunner {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository, CategoryRepository categoryRepository, ProductRepository productRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void run(ApplicationArguments args) {
        createAdminAccount();
        seedCategories();
    }

    private void createAdminAccount() {
        String adminEmail = "adminaccount@gmail.com";

        if (!userRepository.existsByEmail(adminEmail)) {
            User admin = User.create(adminEmail, passwordEncoder.encode("password123"), Role.ADMIN);

            userRepository.save(admin);
        }
    }

    private void seedCategories() {
        if (categoryRepository.count() > 0) {
            return;
        }

        Category electronics = categoryRepository.save(
            Category.create("Electronics", "Electronic devices and accessories")
        );

        Category clothing = categoryRepository.save(
            Category.create("Clothing", "Apparel and fashion items")
        );

        Category food = categoryRepository.save(
            Category.create("Food & Beverages", "Food products and drinks")
        );

        seedProducts(electronics, clothing, food);
    }

    private void seedProducts(Category electronics, Category clothing, Category food) {
        if (productRepository.count() > 0) {
            return;
        }

        productRepository.save(Product.create(
            "Smartphone X12",
            "Latest generation smartphone with 128GB storage",
            new BigDecimal("2999.99"),
            50,
            electronics
        ));

        productRepository.save(Product.create(
            "Wireless Headphones",
            "Noise cancelling bluetooth headphones",
            new BigDecimal("599.99"),
            30,
            electronics
        ));

        productRepository.save(Product.create(
            "Running Shoes",
            "Lightweight running shoes for all terrains",
            new BigDecimal("349.99"),
            100,
            clothing
        ));

        productRepository.save(Product.create(
            "Winter Jacket",
            "Waterproof jacket for cold weather",
            new BigDecimal("499.99"),
            40,
            clothing
        ));

        productRepository.save(Product.create(
            "Premium Coffee Beans",
            "Single origin arabica coffee beans 500g",
            new BigDecimal("79.99"),
            200,
            food
        ));
    }
}
