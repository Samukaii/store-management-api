package org.samuel.storemanagement.domain.preparation.ingredient.events;

import lombok.RequiredArgsConstructor;
import org.samuel.storemanagement.domain.preparation.ingredient.models.PreparationIngredient;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PreparationIngredientEventPublisher {
    private final ApplicationEventPublisher publisher;

    public void emitChange(PreparationIngredient preparationIngredient) {
        publisher.publishEvent(new PreparationIngredientChangeEvent(preparationIngredient));
    }
}

