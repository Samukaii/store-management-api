package org.samuel.storemanagement.domain.preparation.preparation.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PreparationCreate {
    @NotNull(message = "Campo obrigatório")
    String name;
}
