package org.samuel.storemanagement.domain.order.order.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.samuel.storemanagement.domain.order.order.dtos.OrderResponse;
import org.samuel.storemanagement.domain.order.order.models.Order;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderResponse toDto(Order order);
    List<OrderResponse> toListDto(List<Order> order);
}
