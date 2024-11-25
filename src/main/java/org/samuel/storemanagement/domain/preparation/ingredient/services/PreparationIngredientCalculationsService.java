package org.samuel.storemanagement.domain.preparation.ingredient.services;

import lombok.RequiredArgsConstructor;
import org.samuel.storemanagement.domain.preparation.ingredient.enumerations.PreparationIngredientType;
import org.samuel.storemanagement.domain.preparation.ingredient.events.PreparationIngredientEventPublisher;
import org.samuel.storemanagement.domain.preparation.ingredient.models.PreparationIngredient;
import org.samuel.storemanagement.domain.preparation.ingredient.repositories.PreparationIngredientRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PreparationIngredientCalculationsService {
    private final PreparationIngredientRepository repository;
    private final PreparationIngredientEventPublisher publisher;

    public void calculateTotalCost(PreparationIngredient preparationIngredient) {
        preparationIngredient.setTotalCost(getTotalCost(preparationIngredient));
    }

    public void save(PreparationIngredient preparation) {
        save(preparation, true);
    }

    public void save(PreparationIngredient preparation, Boolean emitChanges) {
        PreparationIngredient saved = repository.save(preparation);

        if (emitChanges) publisher.emitChange(saved);
    }

    private Double getTotalCost(PreparationIngredient ingredient) {
        return ingredient.getIngredientType() == PreparationIngredientType.RAW_MATERIAL
                ? ingredient.getRawMaterial().getCostPerUnit() * ingredient.getQuantity()
                : ingredient.getCustomCost();
    }
}
