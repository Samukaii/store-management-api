package org.samuel.storemanagement.domain.preparation.preparation.dtos;

import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.samuel.storemanagement.general.enumerations.MeasurementUnit;

@Data
public class PreparationUpdate {
    String name;
    @Positive(message = "A quantidade precisa ser maior que zero")
    Double quantity;
    MeasurementUnit measurementUnit;
}
