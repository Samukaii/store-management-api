package org.samuel.storemanagement.domain.order.order.events;

import lombok.RequiredArgsConstructor;
import org.samuel.storemanagement.domain.order.order.models.Order;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OrderEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public void emitChanges(Order order) {
        applicationEventPublisher.publishEvent(new OrderChangeEvent(order));
    }
}
