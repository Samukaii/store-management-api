package org.samuel.storemanagement.domain.order.item.events;

import lombok.RequiredArgsConstructor;
import org.samuel.storemanagement.domain.order.item.services.OrderItemCalculationsService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OrderItemEventHandler {
    private final OrderItemCalculationsService service;

    @EventListener
    @Async
    public void updateAssociatedProduct(OrderItemChangeEvent event) {
        service.updateAssociatedProduct(event.getOrderItem());
    }
}

