package org.samuel.storemanagement.domain.preparation.ingredient.dtos;

import lombok.Data;
import org.samuel.storemanagement.general.dto.BaseOption;

@Data
public class PreparationIngredientViewResponse {
    Long id;
    Double totalCost;
    Double quantity;
    String name;
    BaseOption measurementUnit;
}
