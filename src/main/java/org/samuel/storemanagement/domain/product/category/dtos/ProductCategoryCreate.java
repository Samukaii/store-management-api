package org.samuel.storemanagement.domain.product.category.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProductCategoryCreate {
    @NotBlank(message = "O nome não pode estar vazio")
    String name;
}
