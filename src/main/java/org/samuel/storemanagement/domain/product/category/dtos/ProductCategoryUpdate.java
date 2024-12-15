package org.samuel.storemanagement.domain.product.category.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ProductCategoryUpdate {
    @NotEmpty(message = "O nome não pode estar vazio")
    String name;
}
