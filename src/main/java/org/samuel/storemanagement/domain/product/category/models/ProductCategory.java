package org.samuel.storemanagement.domain.product.category.models;

import jakarta.persistence.*;
import lombok.Data;
import org.samuel.storemanagement.domain.product.product.models.Product;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products_categories")
@Data
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany
    @JoinColumn(name = "category_id")
    private List<Product> products = new ArrayList<>();
}
