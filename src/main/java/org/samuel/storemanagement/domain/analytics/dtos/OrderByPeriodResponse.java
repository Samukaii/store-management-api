package org.samuel.storemanagement.domain.analytics.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class OrderByPeriodResponse {
    Double total;
    LocalDate date;
    Integer quantity;
}
