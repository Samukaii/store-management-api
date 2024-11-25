package org.samuel.storemanagement.domain.product.ingredient.mappers;

import org.mapstruct.*;
import org.samuel.storemanagement.domain.product.ingredient.dtos.ProductIngredientCreate;
import org.samuel.storemanagement.domain.product.ingredient.dtos.ProductIngredientResponse;
import org.samuel.storemanagement.domain.product.ingredient.models.ProductIngredient;
import org.samuel.storemanagement.general.dto.BaseOption;
import org.samuel.storemanagement.general.enumerations.MeasurementUnit;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductIngredientMapper {
    @Mapping(source = "productIngredient", target = "measurementUnit", qualifiedByName = "measurementUnit")
    @Mapping(source = "productIngredient", target = "name", qualifiedByName = "name")
    @Mapping(source = "productIngredient", target = "quantity", qualifiedByName = "quantity")
    ProductIngredientResponse toDto(ProductIngredient productIngredient);

    List<ProductIngredientResponse> toListDto(List<ProductIngredient> foodInput);

    ProductIngredient toEntity(ProductIngredientCreate productIngredientResponse);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget ProductIngredient productIngredient, ProductIngredientCreate payload);

    @Named("measurementUnit")
    default BaseOption getHumanizedMeasurementUnit(ProductIngredient productIngredient) {
        MeasurementUnit unit = getMeasurementUnit(productIngredient);

        double quantity = productIngredient.getQuantity() == null ? 0 : productIngredient.getQuantity();

        String name = switch (unit) {
            case KILOGRAMS -> quantity < 1 ? "g" : "kg";
            case LITER -> quantity < 1 ? "ml" : "L";
            case PORTION -> "porção";
            default -> "unidade(s)";
        };

        return BaseOption.builder()
                .id(unit.ordinal())
                .name(name).build();
    }

    @Named("name")
    default String getName(ProductIngredient productIngredient) {
        return switch (productIngredient.getIngredientType()) {
            case RAW_MATERIAL -> productIngredient.getRawMaterial().getName();
            case PREPARATION -> productIngredient.getPreparation().getName();
            default -> productIngredient.getCustomName();
        };
    }

    @Named("quantity")
    default Double getQuantity(ProductIngredient productIngredient) {
        MeasurementUnit unit = getMeasurementUnit(productIngredient);
        double quantity = productIngredient.getQuantity() == null ? 0 : productIngredient.getQuantity();

        return switch (unit) {
            case KILOGRAMS, LITER -> quantity < 1 ? quantity * 1000 : quantity;
            case PORTION -> 1d;
            default -> quantity;
        };
    }

    default MeasurementUnit getMeasurementUnit(ProductIngredient productIngredient) {
        return switch (productIngredient.getIngredientType()) {
            case RAW_MATERIAL -> productIngredient.getRawMaterial().getMeasurementUnit();
            case PREPARATION -> productIngredient.getPreparation().getMeasurementUnit();
            default -> MeasurementUnit.PORTION;
        };
    }
}
