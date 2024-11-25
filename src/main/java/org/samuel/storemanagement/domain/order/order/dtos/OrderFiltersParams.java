package org.samuel.storemanagement.domain.order.order.dtos;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class OrderFiltersParams {
    String sortProperty;
    String sortDirection;
    ZonedDateTime startDate;
    ZonedDateTime endDate;
}
