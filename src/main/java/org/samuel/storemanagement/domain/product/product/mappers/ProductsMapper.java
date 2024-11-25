package org.samuel.storemanagement.domain.product.product.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.samuel.storemanagement.domain.product.category.dtos.ProductCategoryViewResponse;
import org.samuel.storemanagement.domain.product.category.mappers.ProductCategoryMapper;
import org.samuel.storemanagement.domain.product.category.models.ProductCategory;
import org.samuel.storemanagement.domain.product.product.dtos.ProductResponse;
import org.samuel.storemanagement.domain.product.product.dtos.ProductUpdate;
import org.samuel.storemanagement.domain.product.product.models.Product;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductsMapper {
    ProductResponse toDto(Product product);

    List<ProductResponse> toListDto(List<Product> products);

    Product toEntity(ProductUpdate productResponse);

    default ProductCategoryViewResponse categoryToDto(ProductCategory category) {
        return Mappers.getMapper(ProductCategoryMapper.class).toDto(category);
    }
}
