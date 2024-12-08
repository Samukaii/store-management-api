package org.samuel.storemanagement.domain.preparation.ingredient.mappers;

import org.mapstruct.*;
import org.samuel.storemanagement.domain.preparation.ingredient.dtos.PreparationIngredientCreate;
import org.samuel.storemanagement.domain.preparation.ingredient.dtos.PreparationIngredientViewResponse;
import org.samuel.storemanagement.domain.preparation.ingredient.enumerations.PreparationIngredientType;
import org.samuel.storemanagement.domain.preparation.ingredient.models.PreparationIngredient;
import org.samuel.storemanagement.general.dto.BaseOption;
import org.samuel.storemanagement.general.enumerations.MeasurementUnit;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PreparationIngredientMapper {

    PreparationIngredient toEntity(PreparationIngredientCreate preparationIngredient);

    @Mapping(source = "ingredient", target = "measurementUnit", qualifiedByName = "measurementUnit")
    @Mapping(source = "ingredient", target = "name", qualifiedByName = "name")
    @Mapping(source = "ingredient", target = "quantity", qualifiedByName = "quantity")
    PreparationIngredientViewResponse toDto(PreparationIngredient ingredient);

    List<PreparationIngredientViewResponse> toListDto(List<PreparationIngredient> foodInput);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget PreparationIngredient productIngredient, PreparationIngredientCreate payload);

    @Named("name")
    default String getName(PreparationIngredient productIngredient) {
        return productIngredient.getIngredientType() == PreparationIngredientType.RAW_MATERIAL
                ? productIngredient.getRawMaterial().getName()
                : productIngredient.getCustomName();
    }


    @Named("measurementUnit")
    default BaseOption getHumanizedMeasurementUnit(PreparationIngredient ingredient) {
        MeasurementUnit unit = getMeasurementUnit(ingredient);

        double quantity = ingredient.getQuantity() == null ? 0 : ingredient.getQuantity();

        String name = switch (unit) {
            case KILOGRAMS -> quantity < 1 ? "g" : "kg";
            case LITER -> quantity < 1 ? "ml" : "L";
            case PORTION -> "porção";
            default -> "unidade(s)";
        };

        return BaseOption.builder()
                .id((long) unit.ordinal())
                .name(name).build();
    }

    @Named("quantity")
    default Double getQuantity(PreparationIngredient ingredient) {
        MeasurementUnit unit = getMeasurementUnit(ingredient);
        double quantity = ingredient.getQuantity() == null ? 0 : ingredient.getQuantity();

        return switch (unit) {
            case KILOGRAMS, LITER -> quantity < 1 ? quantity * 1000 : quantity;
            case PORTION -> 1d;
            default -> quantity;
        };
    }

    default MeasurementUnit getMeasurementUnit(PreparationIngredient productIngredient) {
        return productIngredient.getIngredientType() == PreparationIngredientType.RAW_MATERIAL
                ? productIngredient.getRawMaterial().getMeasurementUnit()
                : MeasurementUnit.PORTION;
    }
}
