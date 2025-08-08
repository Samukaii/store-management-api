package org.samuel.storemanagement.domain.order.order.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.samuel.storemanagement.domain.analytics.dtos.OrderByPeriodResponse;
import org.samuel.storemanagement.domain.order.item.mappers.OrderItemMapper;
import org.samuel.storemanagement.domain.order.item.models.OrderItem;
import org.samuel.storemanagement.domain.order.item.services.OrderItemService;
import org.samuel.storemanagement.domain.order.order.dtos.ImportedOrder;
import org.samuel.storemanagement.domain.order.order.dtos.OrderCreateDTO;
import org.samuel.storemanagement.domain.order.order.dtos.OrderImportedDTO;
import org.samuel.storemanagement.domain.order.order.enumerations.PeriodType;
import org.samuel.storemanagement.domain.order.order.events.OrderEventPublisher;
import org.samuel.storemanagement.domain.order.order.exceptions.OrderNotFoundException;
import org.samuel.storemanagement.domain.order.order.models.Order;
import org.samuel.storemanagement.domain.order.order.repositories.OrderRepository;
import org.samuel.storemanagement.general.dto.FilePayload;
import org.samuel.storemanagement.general.filters.FilterSpecificationService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository repository;
    private final OrderItemService orderItemService;
    private final OrderEventPublisher publisher;
    private final FilterSpecificationService<Order> specificationService;
    private final OrderItemMapper orderItemMapper;

    public void importOrders(FilePayload payload) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        List<ImportedOrder> importedOrders = objectMapper.readValue(
                payload.getFile().getInputStream(),
                new TypeReference<>() {
                }
        );

        importedOrders.forEach(this::create);
    }

    public void create(OrderCreateDTO payload) {
        Order newOrder = Order.builder()
                .date(payload.getDate())
                .build();

        Order result = recalculateAndSave(newOrder);

        orderItemService.addProducts(result, payload.getProducts());
    }

    private void create(ImportedOrder imported) {
        Order existentOrder = repository.findByCode(imported.getTitle()).orElse(null);

        if (existentOrder != null) return;

        Order newOrder = Order.builder()
                .code(imported.getTitle())
                .date(toZoneDate(imported.getDate()))
                .customerInfo(imported.getCustomerInfo())
                .items(orderItemMapper.toListModel(imported.getItems()))
                .build();

        Order result = recalculateAndSave(newOrder);

        orderItemService.importOrderItems(result, imported.getItems());
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    private ZonedDateTime toZoneDate(String text) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");

        return ZonedDateTime.parse(text, formatter);
    }

    public Order findById(Long id) throws OrderNotFoundException {
        Optional<Order> foundFoodInput = repository.findById(id);

        return foundFoodInput.orElseThrow(OrderNotFoundException::new);
    }

    public List<Order> findAll(Map<String, String> params) {
        return repository.findAll(specificationService.buildSpecification(params));
    }

    public List<OrderByPeriodResponse> findAllByPeriod(Map<String, String> params) {
        List<Order> orders = repository.findAll(specificationService.buildSpecification(params));

        PeriodType periodType = PeriodType.values()[Integer.parseInt(params.getOrDefault("periodType", "0"))];

        Map<?, OrderByPeriodResponse> groupedOrders = orders.stream()
                .collect(Collectors.groupingBy(
                                order -> {
                                    if (periodType == PeriodType.DAY) return order.getDate().toLocalDate();

                                    return YearMonth.from(order.getDate().toLocalDate());
                                },
                                LinkedHashMap::new,
                                Collectors.collectingAndThen(
                                        Collectors.toList(),
                                        dailyOrders -> OrderByPeriodResponse.builder()
                                                .total(dailyOrders.stream().mapToDouble(Order::getTotal).sum())
                                                .quantity(dailyOrders.size())
                                                .build()
                                )
                        )
                );

        List<OrderByPeriodResponse> periods = new ArrayList<>();

        groupedOrders.forEach((date, period) -> {
            if (date instanceof LocalDate) {
                period.setDate((LocalDate) date);
            } else if (date instanceof YearMonth) {
                period.setDate(((YearMonth) date).atDay(1));
            }

            periods.add(period);
        });

        return periods;
    }

    public Order recalculateAndSave(Order order) {
        calculateTotal(order);

        Order result = repository.save(order);

        publisher.emitChanges(result);

        return result;
    }

    private void calculateTotal(Order order) {
        if (order.getItems() == null) {
            order.setTotal(0d);
            return;
        }

        double total = order.getItems().stream().mapToDouble(OrderItem::getTotal).sum();

        order.setTotal(total);
    }
}
