package org.samuel.storemanagement.domain.preparation.preparation.dtos;

import lombok.Data;
import org.samuel.storemanagement.general.dto.BaseOption;

@Data
public class PreparationViewResponse {
    Long id;
    String name;
    Double quantity;
    BaseOption measurementUnit;
    Double totalCost;
    Double costPerUnit;
}
