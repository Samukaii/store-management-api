package org.samuel.storemanagement.domain.product.ingredient.repositories;

import org.samuel.storemanagement.domain.product.ingredient.models.ProductIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface ProductIngredientRepository extends JpaRepository<ProductIngredient, Long>, JpaSpecificationExecutor<ProductIngredient>, ProductIngredientRepositoryCustom {
    Optional<ProductIngredient> findByIdAndProductId(long productFoodInputId, long productId);

    List<ProductIngredient> findAllByProductId(long productId);

    void deleteByIdAndProductId(long productFoodInputId, long productId);
}
