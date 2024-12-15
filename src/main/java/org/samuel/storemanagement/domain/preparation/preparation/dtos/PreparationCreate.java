package org.samuel.storemanagement.domain.preparation.preparation.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PreparationCreate {
    @NotBlank(message = "O nome não pode estar vazio")
    String name;
}
