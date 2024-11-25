package org.samuel.storemanagement.domain.order.order.events;

import lombok.Getter;
import lombok.Setter;
import org.samuel.storemanagement.domain.order.order.models.Order;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class OrderChangeEvent extends ApplicationEvent {
    private Order order;

    public OrderChangeEvent(Order event) {
        super(event);
        order = event;
    }
}
