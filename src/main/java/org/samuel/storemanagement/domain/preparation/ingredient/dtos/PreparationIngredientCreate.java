package org.samuel.storemanagement.domain.preparation.ingredient.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.samuel.storemanagement.domain.preparation.ingredient.enumerations.PreparationIngredientType;

@Data
public class PreparationIngredientCreate {
    Long rawMaterialId;
    @Positive(message = "A quantidade deve ser maior que zero")
    Double quantity;
    @NotNull(message = "Campo obrigatório")
    PreparationIngredientType ingredientType;
    Double customCost;
    String customName;
}
