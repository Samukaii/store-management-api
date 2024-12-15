package org.samuel.storemanagement.domain.product.product.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProductCreate {
    @NotBlank(message = "O nome não pode estar vazio")
    String name;
    String integrationName;
    Long categoryId;
}
