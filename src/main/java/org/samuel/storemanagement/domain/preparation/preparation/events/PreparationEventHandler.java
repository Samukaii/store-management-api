package org.samuel.storemanagement.domain.preparation.preparation.events;

import lombok.RequiredArgsConstructor;
import org.samuel.storemanagement.domain.preparation.ingredient.events.PreparationIngredientChangeEvent;
import org.samuel.storemanagement.domain.preparation.ingredient.models.PreparationIngredient;
import org.samuel.storemanagement.domain.preparation.preparation.services.PreparationCalculationsService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PreparationEventHandler {
    private final PreparationCalculationsService calculationsService;

    @EventListener
    @Async
    public void updateTotalCost(PreparationIngredientChangeEvent event) {
        PreparationIngredient ingredient = event.getPreparationIngredient();

        if(ingredient.getPreparation() == null) return;

        calculationsService.calculateCosts(ingredient.getPreparation());

        calculationsService.save(ingredient.getPreparation());
    }
}
