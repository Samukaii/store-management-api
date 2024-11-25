package org.samuel.storemanagement.domain.product.category.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.samuel.storemanagement.domain.product.category.dtos.ProductCategoryCreate;
import org.samuel.storemanagement.domain.product.category.dtos.ProductCategoryViewResponse;
import org.samuel.storemanagement.domain.product.category.models.ProductCategory;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductCategoryMapper {
    ProductCategoryViewResponse toDto(ProductCategory category);
    List<ProductCategoryViewResponse> toListDto(List<ProductCategory> category);

    ProductCategory toEntity(ProductCategoryCreate category);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(ProductCategoryCreate payload, @MappingTarget ProductCategory entity);
}
