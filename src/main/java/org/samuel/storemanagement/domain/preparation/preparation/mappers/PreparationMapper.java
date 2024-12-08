package org.samuel.storemanagement.domain.preparation.preparation.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.samuel.storemanagement.domain.preparation.preparation.dtos.PreparationCreate;
import org.samuel.storemanagement.domain.preparation.preparation.dtos.PreparationUpdate;
import org.samuel.storemanagement.domain.preparation.preparation.dtos.PreparationViewResponse;
import org.samuel.storemanagement.domain.preparation.preparation.models.Preparation;
import org.samuel.storemanagement.general.dto.BaseOption;
import org.samuel.storemanagement.general.enumerations.MeasurementUnit;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PreparationMapper {
    PreparationViewResponse toDto(Preparation foodInput);

    Preparation toModel(PreparationCreate preparation);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(PreparationUpdate payload, @MappingTarget Preparation entity);

    List<PreparationViewResponse> toListDto(List<Preparation> foodInput);

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
