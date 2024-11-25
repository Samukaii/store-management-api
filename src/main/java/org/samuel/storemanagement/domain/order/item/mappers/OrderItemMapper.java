package org.samuel.storemanagement.domain.order.item.mappers;

import org.mapstruct.Mapper;
import org.samuel.storemanagement.domain.order.item.dtos.OrderItemCreate;
import org.samuel.storemanagement.domain.order.item.dtos.OrderItemResponse;
import org.samuel.storemanagement.domain.order.item.models.OrderItem;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    OrderItemResponse toDto(OrderItem orderItem);

    List<OrderItemResponse> toListDto(List<OrderItem> foodInput);

    OrderItem toModel(OrderItemCreate payload);

    List<OrderItem> toListModel(List<OrderItemCreate> payload);
}
