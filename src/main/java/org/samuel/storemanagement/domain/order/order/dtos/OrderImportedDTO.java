package org.samuel.storemanagement.domain.order.order.dtos;

import lombok.Data;
import org.samuel.storemanagement.domain.order.item.dtos.OrderItemCreate;

import java.util.List;

@Data
public class OrderImportedDTO {
    String title;
    String date;
    String error;
    String customerInfo;
    List<OrderItemCreate> items;
}
