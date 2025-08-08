package org.samuel.storemanagement.domain.rawMaterial.rawMaterial.mappers;

import org.mapstruct.*;
import org.samuel.storemanagement.domain.rawMaterial.rawMaterial.dtos.RawMaterialCreate;
import org.samuel.storemanagement.domain.rawMaterial.rawMaterial.dtos.RawMaterialUpdate;
import org.samuel.storemanagement.domain.rawMaterial.rawMaterial.dtos.RawMaterialViewResponse;
import org.samuel.storemanagement.domain.rawMaterial.rawMaterial.models.RawMaterial;
import org.samuel.storemanagement.general.dto.BaseOption;
import org.samuel.storemanagement.general.enumerations.MeasurementUnit;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RawMaterialMapper {
    @Mapping(source = "measurementUnit", target = "measurementUnit", qualifiedByName = "measurementUnitOption")
    RawMaterialViewResponse toDto(RawMaterial rawMaterial);

    @Mapping(source = "measurementUnit", target = "measurementUnit", qualifiedByName = "measurementUnitOption")
    List<RawMaterialViewResponse> toListDto(List<RawMaterial> rawMaterials);

    RawMaterial toModel(RawMaterialCreate payload);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(RawMaterialUpdate payload, @MappingTarget RawMaterial entity);


    @Named("measurementUnitOption")
    default BaseOption unitMeasurementToOption(MeasurementUnit measurementUnit) {
        var baseOption = new BaseOption();

        baseOption.setId((long) measurementUnit.ordinal());

        switch (measurementUnit) {
            case UNIT -> baseOption.setName("Unidade");
            case LITER -> baseOption.setName("L");
            case KILOGRAMS -> baseOption.setName("Kg");
        }

        return baseOption;
    }
}
