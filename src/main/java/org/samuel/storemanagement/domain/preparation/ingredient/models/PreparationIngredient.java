package org.samuel.storemanagement.domain.preparation.ingredient.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.samuel.storemanagement.domain.preparation.ingredient.enumerations.PreparationIngredientType;
import org.samuel.storemanagement.domain.preparation.preparation.models.Preparation;
import org.samuel.storemanagement.domain.rawMaterial.rawMaterial.models.RawMaterial;

@Entity
@Table(name = "preparation_ingredients")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PreparationIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne
    @JoinColumn(name = "raw_material_id")
    private RawMaterial rawMaterial;

    @ManyToOne
    @JoinColumn(name = "preparation_id")
    private Preparation preparation;

    Double quantity;

    Double totalCost;
    PreparationIngredientType ingredientType;

    String customName;
    Double customCost = 0d;
}
