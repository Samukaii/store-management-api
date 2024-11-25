package org.samuel.storemanagement.domain.product.ingredient.events;

import lombok.Getter;
import lombok.Setter;
import org.samuel.storemanagement.domain.product.ingredient.models.ProductIngredient;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class ProductIngredientChangeEvent extends ApplicationEvent {
    private ProductIngredient productIngredient;

    public ProductIngredientChangeEvent(ProductIngredient event) {
        super(event);
        productIngredient = event;
    }
}

