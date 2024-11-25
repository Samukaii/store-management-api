package org.samuel.storemanagement.domain.order.order.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.samuel.storemanagement.domain.order.item.models.OrderItem;

import java.time.ZonedDateTime;
import java.util.List;

@Builder
@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String customerInfo;

    private ZonedDateTime date;
    private Double total = 0d;

    @OneToMany
    @JoinColumn(name = "order_id")
    private List<OrderItem> items;
}
