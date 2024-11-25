package org.samuel.storemanagement.domain.product.category.events;

import lombok.RequiredArgsConstructor;
import org.samuel.storemanagement.domain.product.category.models.ProductCategory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ProductCategoryEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public void emitChanges(ProductCategory category) {
        applicationEventPublisher.publishEvent(new ProductCategoryChangeEvent(category));
    }
}
