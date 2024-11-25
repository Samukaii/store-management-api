package org.samuel.storemanagement.domain.preparation.preparation.dtos;

import lombok.Data;
import org.samuel.storemanagement.general.enumerations.MeasurementUnit;

@Data
public class PreparationUpdate {
    String name;
    Double quantity;
    MeasurementUnit measurementUnit;
}
