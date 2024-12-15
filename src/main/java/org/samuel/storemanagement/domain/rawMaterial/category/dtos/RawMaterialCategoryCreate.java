package org.samuel.storemanagement.domain.rawMaterial.category.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RawMaterialCategoryCreate {
    @NotBlank(message = "O nome não pode estar vazio")
    String name;
}
