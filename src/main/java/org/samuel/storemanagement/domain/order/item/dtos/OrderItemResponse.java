package org.samuel.storemanagement.domain.order.item.dtos;

import lombok.Data;

@Data
public class OrderItemResponse {
    Long id;
    String name;
    Integer quantity;
    Double total;
}
