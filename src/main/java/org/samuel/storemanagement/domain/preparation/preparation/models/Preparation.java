package org.samuel.storemanagement.domain.preparation.preparation.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.samuel.storemanagement.domain.preparation.ingredient.models.PreparationIngredient;
import org.samuel.storemanagement.domain.product.ingredient.models.ProductIngredient;
import org.samuel.storemanagement.general.enumerations.MeasurementUnit;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "preparations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Preparation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    private Double totalCost = 0d;
    private Double costPerUnit = 0d;
    private Double quantity = 0d;
    private MeasurementUnit measurementUnit = MeasurementUnit.KILOGRAMS;

    @OneToMany
    @JoinColumn(name = "preparation_id")
    private List<PreparationIngredient> ingredients = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "preparation_id")
    private List<ProductIngredient> productIngredients = new ArrayList<>();
}
