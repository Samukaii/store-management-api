package org.samuel.storemanagement.domain.preparation.ingredient.events;

import lombok.Getter;
import lombok.Setter;
import org.samuel.storemanagement.domain.preparation.ingredient.models.PreparationIngredient;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class PreparationIngredientChangeEvent extends ApplicationEvent {
    private PreparationIngredient preparationIngredient;

    public PreparationIngredientChangeEvent(PreparationIngredient event) {
        super(event);
        preparationIngredient = event;
    }
}

