package org.samuel.storemanagement.domain.preparation.ingredient.events;

import lombok.RequiredArgsConstructor;
import org.samuel.storemanagement.domain.preparation.ingredient.services.PreparationIngredientCalculationsService;
import org.samuel.storemanagement.domain.rawMaterial.rawMaterial.events.RawMaterialPersistEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PreparationIngredientEventHandler {
    private final PreparationIngredientCalculationsService calculationsService;

    @EventListener
    @Async
    public void updateTotalCost(RawMaterialPersistEvent event) {
        if (event.getRawMaterial().getPreparationIngredients() == null) return;

        event.getRawMaterial().getPreparationIngredients().forEach(ingredient -> {
                    calculationsService.calculateTotalCost(ingredient);
                    calculationsService.save(ingredient);
                }
        );
    }
}

