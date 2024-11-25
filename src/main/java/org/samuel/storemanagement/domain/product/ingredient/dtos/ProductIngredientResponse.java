package org.samuel.storemanagement.domain.product.ingredient.dtos;

import lombok.Data;
import org.samuel.storemanagement.general.dto.BaseOption;

@Data
public class ProductIngredientResponse {
    long id;
    Double totalCost;
    Double quantity;
    String name;
    BaseOption measurementUnit;
}
