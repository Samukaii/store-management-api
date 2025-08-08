package org.samuel.storemanagement.domain.order.order.dtos;

import lombok.Data;
import org.samuel.storemanagement.domain.order.item.dtos.OrderItemCreate;

import java.time.ZonedDateTime;
import java.util.List;

@Data
public class OrderCreateDTO {
    ZonedDateTime date;
    List<OrderItemCreate> products;
}
