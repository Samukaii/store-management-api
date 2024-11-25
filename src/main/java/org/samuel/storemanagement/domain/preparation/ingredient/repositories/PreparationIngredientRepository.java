package org.samuel.storemanagement.domain.preparation.ingredient.repositories;

import org.samuel.storemanagement.domain.preparation.ingredient.models.PreparationIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PreparationIngredientRepository extends JpaRepository<PreparationIngredient, Long> {
    Optional<PreparationIngredient> findByIdAndPreparationId(long productFoodInputId, long productId);
    List<PreparationIngredient> findAllByPreparationId(long productId);
    void deleteByIdAndPreparationId(long productFoodInputId, long productId);
}
