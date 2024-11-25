package org.samuel.storemanagement.domain.product.ingredient.events;

import lombok.RequiredArgsConstructor;
import org.samuel.storemanagement.domain.product.ingredient.models.ProductIngredient;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductIngredientEventPublisher {
    private final ApplicationEventPublisher publisher;

    public void emitChange(ProductIngredient productIngredient) {
        publisher.publishEvent(new ProductIngredientChangeEvent(productIngredient));
    }
}

