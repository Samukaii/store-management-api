package org.samuel.storemanagement.domain.product.ingredient.events;

import lombok.RequiredArgsConstructor;
import org.samuel.storemanagement.domain.preparation.preparation.events.PreparationChangeEvent;
import org.samuel.storemanagement.domain.product.ingredient.services.ProductIngredientService;
import org.samuel.storemanagement.domain.rawMaterial.rawMaterial.events.RawMaterialPersistEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ProductIngredientEventHandler {
    private final ProductIngredientService service;

    @EventListener
    @Async
    public void updateTotalCost(RawMaterialPersistEvent event) {
        if(event.getRawMaterial().getIngredients() == null) return;

        event.getRawMaterial().getIngredients().forEach(service::recalculateAndSave);
    }

    @EventListener
    @Async
    public void updateTotalCost(PreparationChangeEvent event) {
        if(event.getPreparation().getIngredients() == null) return;

        event.getPreparation().getProductIngredients().forEach(service::recalculateAndSave);
    }
}

