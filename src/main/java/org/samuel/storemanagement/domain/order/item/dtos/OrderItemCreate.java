package org.samuel.storemanagement.domain.order.item.dtos;

import lombok.Data;

@Data
public class OrderItemCreate {
    String name;
    Double price;
    Integer quantity;
    Double total;
}
