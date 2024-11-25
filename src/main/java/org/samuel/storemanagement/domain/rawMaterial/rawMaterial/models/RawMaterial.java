package org.samuel.storemanagement.domain.rawMaterial.rawMaterial.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.samuel.storemanagement.domain.preparation.ingredient.models.PreparationIngredient;
import org.samuel.storemanagement.domain.product.ingredient.models.ProductIngredient;
import org.samuel.storemanagement.domain.rawMaterial.category.models.RawMaterialCategory;
import org.samuel.storemanagement.general.enumerations.MeasurementUnit;

import java.util.List;

@Entity
@Table(name = "raw_materials")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RawMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double cost;

    private Double quantity;

    private MeasurementUnit measurementUnit;

    private Double costPerUnit;

    @ManyToOne
    @JoinColumn(name="category_id")
    private RawMaterialCategory category;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="raw_material_id")
    private List<ProductIngredient> ingredients;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="raw_material_id")
    private List<PreparationIngredient> preparationIngredients;
}
