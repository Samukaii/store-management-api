package org.samuel.storemanagement.domain.rawMaterial.rawMaterial.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.samuel.storemanagement.general.enumerations.MeasurementUnit;

@Data
public class RawMaterialUpdate {
    @NotEmpty(message = "O nome não pode estar vazio")
    String name;
    @Positive(message = "O custo precisa ser maior que zero")
    Double cost;
    MeasurementUnit measurementUnit;
    Long categoryId;
    @Positive(message = "A quantidade precisa ser maior que zero")
    Double quantity;
}
