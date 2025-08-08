package org.samuel.storemanagement.domain.product.ingredient.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.samuel.storemanagement.general.dto.BaseOption;

@Data
@AllArgsConstructor
public class ProductIngredientResponse {
    Long id;
    Double totalCost;
    Double quantity;
    String name;
    BaseOption measurementUnit;
}
