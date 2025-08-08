package org.samuel.storemanagement.domain.product.ingredient.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.samuel.storemanagement.domain.product.ingredient.enumerations.ProductIngredientType;

@Data
@Builder
public class ProductIngredientCreate {
    @NotNull(message = "Campo obrigatório")
    ProductIngredientType ingredientType;
    @NotNull(message = "Campo obrigatório")
    Double quantity;
    String customName;
    Double customCost;
    Long rawMaterialId;
    Long preparationId;
    Long ingredientProductId;
}
