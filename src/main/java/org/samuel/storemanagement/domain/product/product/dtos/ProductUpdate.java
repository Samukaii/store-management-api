package org.samuel.storemanagement.domain.product.product.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ProductUpdate {
    @NotBlank(message = "O nome não pode estar vazio")
    String name;
    String integrationName;
    @Positive(message = "A quantidade deve ser maior que zero")
    Double price;
    Long categoryId;
}
