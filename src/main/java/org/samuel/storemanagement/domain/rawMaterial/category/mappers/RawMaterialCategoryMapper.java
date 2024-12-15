package org.samuel.storemanagement.domain.rawMaterial.category.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.samuel.storemanagement.domain.rawMaterial.category.dtos.RawMaterialCategoryCreate;
import org.samuel.storemanagement.domain.rawMaterial.category.dtos.RawMaterialCategoryUpdate;
import org.samuel.storemanagement.domain.rawMaterial.category.dtos.RawMaterialCategoryViewResponse;
import org.samuel.storemanagement.domain.rawMaterial.category.models.RawMaterialCategory;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RawMaterialCategoryMapper {
    RawMaterialCategoryViewResponse toDto(RawMaterialCategory category);
    List<RawMaterialCategoryViewResponse> toListDto(List<RawMaterialCategory> category);

    RawMaterialCategory toEntity(RawMaterialCategoryCreate category);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(RawMaterialCategoryUpdate payload, @MappingTarget RawMaterialCategory entity);
}
