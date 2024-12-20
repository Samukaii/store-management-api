package org.samuel.storemanagement.domain.rawMaterial.rawMaterial.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.samuel.storemanagement.general.enumerations.MeasurementUnit;

@Data
public class RawMaterialCreate {
    @NotBlank(message = "O nome não pode estar vazio")
    String name;
    @NotNull(message = "Campo obrigatório")
    @Positive(message = "O custo precisa ser maior que zero")
    Double cost;
    @NotNull(message = "Campo obrigatório")
    MeasurementUnit measurementUnit;
    Long categoryId;
    @NotNull(message = "Campo obrigatório")
    @Positive(message = "A quantidade precisa ser maior que zero")
    Double quantity;
}
