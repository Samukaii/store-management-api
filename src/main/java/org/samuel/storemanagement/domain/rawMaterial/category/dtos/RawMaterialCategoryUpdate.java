package org.samuel.storemanagement.domain.rawMaterial.category.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RawMaterialCategoryUpdate {
    @NotEmpty(message = "O nome não pode estar vazio")
    String name;
}
