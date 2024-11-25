package org.samuel.storemanagement.domain.order.order.dtos;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class OrderResponse {
    Long id;
    String code;
    ZonedDateTime date;
    String customerInfo;
    Double total;
}
