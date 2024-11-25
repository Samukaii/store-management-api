package org.samuel.storemanagement.domain.product.ingredient.repositories;

import org.samuel.storemanagement.domain.product.ingredient.models.ProductIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductIngredientRepository extends JpaRepository<ProductIngredient, Long> {
    Optional<ProductIngredient> findByIdAndProductId(long productFoodInputId, long productId);
    List<ProductIngredient> findAllByProductId(long productId);
    void deleteByIdAndProductId(long productFoodInputId, long productId);
}
