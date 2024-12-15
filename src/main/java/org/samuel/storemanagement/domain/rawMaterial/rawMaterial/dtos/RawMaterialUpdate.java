package org.samuel.storemanagement.domain.rawMaterial.rawMaterial.dtos;

import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.samuel.storemanagement.general.enumerations.MeasurementUnit;

@Data
public class RawMaterialUpdate {
    String name;
    @Positive(message = "O custo precisa ser maior que zero")
    Double cost;
    MeasurementUnit measurementUnit;
    Long categoryId;
    @Positive(message = "A quantidade precisa ser maior que zero")
    Double quantity;
}
