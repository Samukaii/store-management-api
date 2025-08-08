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
