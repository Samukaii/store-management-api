package org.samuel.storemanagement.domain.analytics.dtos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class OrderByPeriodResponse {
    Double total;
    LocalDate date;
    Integer quantity;
}
