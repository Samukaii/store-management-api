package org.samuel.storemanagement.domain.order.item.dtos;

import lombok.Data;
import org.samuel.storemanagement.domain.product.product.dtos.ProductResponse;

@Data
public class OrderItemResponse {
    Long id;
    Integer quantity;
    ProductResponse product;
    Double total;
}
