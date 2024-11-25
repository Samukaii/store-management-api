package org.samuel.storemanagement.domain.rawMaterial.rawMaterial.dtos;

import lombok.Data;
import org.samuel.storemanagement.general.enumerations.MeasurementUnit;

@Data
public class RawMaterialCreate {
    String name;
    Double cost;
    MeasurementUnit measurementUnit;
    Long categoryId;
    Double quantity;
}
