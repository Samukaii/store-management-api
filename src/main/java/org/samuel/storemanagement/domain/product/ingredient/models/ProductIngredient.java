package org.samuel.storemanagement.domain.product.ingredient.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.samuel.storemanagement.domain.preparation.preparation.models.Preparation;
import org.samuel.storemanagement.domain.product.ingredient.enumerations.ProductIngredientType;
import org.samuel.storemanagement.domain.product.product.models.Product;
import org.samuel.storemanagement.domain.rawMaterial.rawMaterial.models.RawMaterial;

@Entity
@Table(name = "product_ingredients")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    private Double customCost = 0d;
    private String customName;

    @ManyToOne
    @JoinColumn(name = "raw_material_id")
    private RawMaterial rawMaterial;

    @ManyToOne
    @JoinColumn(name = "preparation_id")
    private Preparation preparation;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "ingredient_product_id")
    private Product ingredientProduct;

    ProductIngredientType ingredientType;

    Double quantity;

    Double totalCost;
}
