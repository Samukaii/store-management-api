package org.samuel.storemanagement.domain.order.item.dtos;

import lombok.Data;

@Data
public class OrderItemCreate {
    Long productId;
    Integer quantity;
}
