package org.samuel.storemanagement.domain.order.item.events;

import lombok.RequiredArgsConstructor;
import org.samuel.storemanagement.domain.order.item.models.OrderItem;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderItemEventPublisher {
    private final ApplicationEventPublisher publisher;

    public void emitChange(OrderItem orderItem) {
        publisher.publishEvent(new OrderItemChangeEvent(orderItem));
    }
}

