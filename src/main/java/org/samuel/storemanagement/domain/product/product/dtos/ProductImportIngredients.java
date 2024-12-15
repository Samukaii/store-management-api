package org.samuel.storemanagement.domain.product.product.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductImportIngredients {
    @NotNull(message = "Campo obrigatório")
    Long productId;
}
