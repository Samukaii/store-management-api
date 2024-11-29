package org.samuel.storemanagement.domain.product.product.events;

import lombok.RequiredArgsConstructor;
import org.samuel.storemanagement.domain.order.item.events.OrderItemChangeEvent;
import org.samuel.storemanagement.domain.product.ingredient.events.ProductIngredientChangeEvent;
import org.samuel.storemanagement.domain.product.product.services.ProductsCalculationsService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ProductEventHandler {
    private final ProductsCalculationsService calculationsService;

    @EventListener
    @Async
    public void onIngredientChange(ProductIngredientChangeEvent event) {
        calculationsService.applyCalculationChanges(event);
    }

    @EventListener
    @Async
    public void onOrderItemChange(OrderItemChangeEvent event) {
        calculationsService.applyCalculationChanges(event);
    }

    @EventListener
    @Async
    public void onProductChange(ProductEventPostPersist event) {
        calculationsService.applyCalculationChanges(event);
    }
}

