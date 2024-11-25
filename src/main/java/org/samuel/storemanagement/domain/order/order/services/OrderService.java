package org.samuel.storemanagement.domain.order.order.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.samuel.storemanagement.domain.analytics.dtos.OrderByPeriodResponse;
import org.samuel.storemanagement.domain.analytics.dtos.ProductBestSellingResponse;
import org.samuel.storemanagement.domain.order.item.mappers.OrderItemMapper;
import org.samuel.storemanagement.domain.order.item.models.OrderItem;
import org.samuel.storemanagement.domain.order.item.services.OrderItemService;
import org.samuel.storemanagement.domain.order.order.dtos.ImportedOrder;
import org.samuel.storemanagement.domain.order.order.dtos.OrderFiltersParams;
import org.samuel.storemanagement.domain.order.order.events.OrderEventPublisher;
import org.samuel.storemanagement.domain.order.order.exceptions.OrderNotFoundException;
import org.samuel.storemanagement.domain.order.order.models.Order;
import org.samuel.storemanagement.domain.order.order.repositories.OrderRepository;
import org.samuel.storemanagement.domain.product.product.mappers.ProductsMapper;
import org.samuel.storemanagement.general.dto.FilePayload;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    private final ProductsMapper productsMapper;
    private final OrderItemMapper orderItemMapper;

    @SneakyThrows
    public void importOrders(FilePayload payload) {
        ObjectMapper objectMapper = new ObjectMapper();

        List<ImportedOrder> importedOrders = objectMapper.readValue(
                payload.getFile().getInputStream(),
                new TypeReference<>() {
                }
        );

        importedOrders.forEach(this::create);
    }

    @SneakyThrows
    private void create(ImportedOrder imported) {
        Order existentOrder = repository.findByCode(imported.getTitle()).orElse(
                Order.builder()
                        .code(imported.getTitle())
                        .build()
        );

        existentOrder.setDate(toZoneDate(imported.getDate()));
        existentOrder.setCustomerInfo(imported.getCustomerInfo());
        existentOrder.setItems(orderItemMapper.toListModel(imported.getItems()));

        Order result = recalculateAndSave(existentOrder);

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

    public List<ProductBestSellingResponse> getBestSellingProducts(OrderFiltersParams params) {
        List<Order> orders = repository.findAllWithFilters(params);

        List<ProductBestSellingResponse> products = new ArrayList<>();

        orders.forEach(order -> order.getItems().forEach(item -> {
            if(item.getProduct() == null) return;

            ProductBestSellingResponse existent = products.stream().
                    filter(product -> product.getProduct().getId() == item.getProduct().getId()).findFirst()
                    .orElse(null);

            if (existent == null) {
                products.add(ProductBestSellingResponse.builder()
                        .product(productsMapper.toDto(item.getProduct()))
                        .salesQuantity(item.getQuantity())
                        .totalBilled(item.getTotal()).build());

                return;
            }

            existent.setTotalBilled(existent.getTotalBilled() + item.getTotal());
            existent.setSalesQuantity(existent.getSalesQuantity() + item.getQuantity());
        }));

        return products;
    }

    public List<Order> findAll(OrderFiltersParams params) {
        return repository.findAllWithFilters(params);
    }

    public List<OrderByPeriodResponse> findAllByDay() {
        List<Order> orders = repository.findAllByOrderByDateAsc();

        Map<LocalDate, OrderByPeriodResponse> groupedOrders = orders.stream()
                .collect(Collectors.groupingBy(
                                order -> order.getDate().toLocalDate(),
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
            period.setDate(date);

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
