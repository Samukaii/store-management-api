package org.samuel.storemanagement.domain.rawMaterial.rawMaterial.mappers;

import org.mapstruct.*;
import org.samuel.storemanagement.domain.rawMaterial.rawMaterial.dtos.RawMaterialCreate;
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
    void update(RawMaterialCreate payload, @MappingTarget RawMaterial entity);


    @Named("measurementUnitOption")
    default BaseOption unitMeasurementToOption(MeasurementUnit measurementUnit) {
        var builder = BaseOption.builder();

        builder.id((long) measurementUnit.ordinal());

        switch (measurementUnit) {
            case UNIT -> builder.name("Unidade");
            case LITER -> builder.name("L");
            case KILOGRAMS -> builder.name("Kg");
        }

        return builder.build();
    }
}
