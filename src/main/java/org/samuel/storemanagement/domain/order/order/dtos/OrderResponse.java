package org.samuel.storemanagement.domain.order.order.dtos;

import lombok.Data;
import org.samuel.storemanagement.domain.order.item.dtos.OrderItemResponse;
import org.samuel.storemanagement.domain.order.item.models.OrderItem;

import java.time.ZonedDateTime;
import java.util.List;

@Data
public class OrderResponse {
    Long id;
    ZonedDateTime date;
    Double total;
    List<OrderItemResponse> items;
}
