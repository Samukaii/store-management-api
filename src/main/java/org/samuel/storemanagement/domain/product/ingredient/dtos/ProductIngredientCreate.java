package org.samuel.storemanagement.domain.product.ingredient.dtos;

import lombok.Data;
import org.samuel.storemanagement.domain.product.ingredient.enumerations.ProductIngredientType;

@Data
public class ProductIngredientCreate {
    ProductIngredientType ingredientType;
    String customName;
    Double customCost;
    Long rawMaterialId;
    Long preparationId;
    Double quantity;
}
