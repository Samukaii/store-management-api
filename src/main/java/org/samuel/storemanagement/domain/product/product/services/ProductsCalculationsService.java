package org.samuel.storemanagement.domain.product.product.services;

import lombok.RequiredArgsConstructor;
import org.samuel.storemanagement.domain.order.item.events.OrderItemChangeEvent;
import org.samuel.storemanagement.domain.order.item.models.OrderItem;
import org.samuel.storemanagement.domain.order.item.services.OrderItemService;
import org.samuel.storemanagement.domain.product.ingredient.events.ProductIngredientChangeEvent;
import org.samuel.storemanagement.domain.product.ingredient.models.ProductIngredient;
import org.samuel.storemanagement.domain.product.product.events.ProductEventChange;
import org.samuel.storemanagement.domain.product.product.models.Product;
import org.samuel.storemanagement.domain.product.product.repositories.ProductsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProductsCalculationsService {
    private final ProductsRepository repository;
    private final OrderItemService orderItemService;

    public void applyCalculationChanges(OrderItemChangeEvent event) {
        Product product = event.getOrderItem().getProduct();

        if(product == null) return;

        calculateTotalBilled(product);

        repository.save(product);
    }

    public void applyCalculationChanges(ProductIngredientChangeEvent event) {
        Product product = event.getProductIngredient().getProduct();

        if(product == null) return;

        calculateTotalCostAndPriceInfo(product);

        repository.save(product);
    }

    public void applyCalculationChanges(ProductEventChange event) {
        Product previous = event.getPrevious();
        Product current = event.getCurrent();

        if(previous == null || !Objects.equals(previous.getPrice(), current.getPrice())) {
            calculateTotalCostAndPriceInfo(current);
        }

        if(previous == null || !Objects.equals(previous.getIntegrationName(), current.getIntegrationName())) {
            updateAssociatedOrderItems(current);
        }

        repository.save(current);
    }

    private void calculateTotalCostAndPriceInfo(Product product) {
        double totalCost = product.getIngredients()
                .stream()
                .mapToDouble(ProductIngredient::getTotalCost)
                .sum();

        double idealProfitMargin = 0.33;
        double variableRates = 0.26;

        double suggestedPrice = totalCost / (1 - idealProfitMargin - variableRates);

        product.setTotalCost(totalCost);
        product.setSuggestedPrice(suggestedPrice);

        if (product.getPrice() > 0) {
            double variableRatesValue = product.getPrice() * variableRates;

            double profit = product.getPrice() - variableRatesValue - totalCost;
            double profitMargin = (profit / product.getPrice()) * 100;

            product.setProfit(profit);
            product.setProfitMargin(profitMargin);
        }
    }

    private void updateAssociatedOrderItems(Product product) {
        List<OrderItem> orderItems = orderItemService.findAllByProduct(product);

        product.setOrders(orderItems);

        calculateTotalBilled(product);
    }

    private void calculateTotalBilled(Product product) {
        if(product.getOrders() == null) return;

        Integer salesQuantity = product.getOrders()
                .stream()
                .mapToInt(OrderItem::getQuantity)
                .sum();

        double totalBilled = product.getOrders()
                .stream()
                .mapToDouble(OrderItem::getTotal)
                .sum();

        product.setTotalBilled(totalBilled);
        product.setSalesQuantity(salesQuantity);
    }
}
