package org.samuel.storemanagement.domain.product.ingredient.repositories;

import org.samuel.storemanagement.domain.product.ingredient.dtos.ProductIngredientResponse;
import org.samuel.storemanagement.domain.product.ingredient.models.ProductIngredient;
import org.samuel.storemanagement.domain.product.product.models.Product;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ProductIngredientRepositoryCustom {
    List<ProductIngredientResponse> getAllCalculatedProducts(Long productId, Specification<ProductIngredient> specification);
}
