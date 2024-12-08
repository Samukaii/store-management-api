package org.samuel.storemanagement.domain.order.item.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemAutocomplete {
    String id;
    String name;
}
