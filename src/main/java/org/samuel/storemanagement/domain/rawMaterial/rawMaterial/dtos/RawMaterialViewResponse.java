package org.samuel.storemanagement.domain.rawMaterial.rawMaterial.dtos;

import lombok.Data;
import org.samuel.storemanagement.domain.rawMaterial.category.dtos.RawMaterialCategoryViewResponse;
import org.samuel.storemanagement.general.dto.BaseOption;

@Data
public class RawMaterialViewResponse {
    long id;
    String name;
    Double costPerUnit;
    Double cost;
    BaseOption measurementUnit;
    Double quantity;
    RawMaterialCategoryViewResponse category;
}
