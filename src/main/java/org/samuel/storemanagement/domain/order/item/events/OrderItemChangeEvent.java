package org.samuel.storemanagement.domain.order.item.events;

import lombok.Getter;
import lombok.Setter;
import org.samuel.storemanagement.domain.order.item.models.OrderItem;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class OrderItemChangeEvent extends ApplicationEvent {
    private OrderItem orderItem;

    public OrderItemChangeEvent(OrderItem event) {
        super(event);
        orderItem = event;
    }
}

