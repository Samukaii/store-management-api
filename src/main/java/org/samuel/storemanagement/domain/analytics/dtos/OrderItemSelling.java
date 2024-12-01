package org.samuel.storemanagement.domain.analytics.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class OrderItemSelling {
    Integer quantity;
    Double total;
    Double profitMargin;
    String name;
    ZonedDateTime date;
}
