package org.samuel.storemanagement.domain.product.product.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.samuel.storemanagement.domain.order.item.models.OrderItem;
import org.samuel.storemanagement.domain.product.category.models.ProductCategory;
import org.samuel.storemanagement.domain.product.ingredient.models.ProductIngredient;
import org.samuel.storemanagement.general.enumerations.MeasurementUnit;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String integrationName = "";
    private Double price = 0d;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="product_id")
    private List<ProductIngredient> ingredients = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="product_id")
    private List<OrderItem> orders = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id")
    private ProductCategory category;

    private Double totalCost = 0d;
    private Double suggestedPrice = 0d;
    private Double profitMargin = 0d;
    private Double profit = 0d;
    private Integer salesQuantity = 0;
    private Double totalBilled = 0d;
    private Double costPerUnit = 0d;
    private Double quantity = 0d;
    private MeasurementUnit measurementUnit = MeasurementUnit.UNIT;
}
