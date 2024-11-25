package org.samuel.storemanagement.domain.preparation.ingredient.dtos;

import lombok.Data;
import org.samuel.storemanagement.domain.preparation.ingredient.enumerations.PreparationIngredientType;

@Data
public class PreparationIngredientCreate {
    Long rawMaterialId;
    Double quantity;
    PreparationIngredientType ingredientType;
    Double customCost;
    String customName;
}
