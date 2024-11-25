package org.samuel.storemanagement.domain.product.product.events;

import lombok.RequiredArgsConstructor;
import org.samuel.storemanagement.domain.product.product.models.Product;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductEventPublisher {
    private final ApplicationEventPublisher publisher;
    private Product previous;

    public void emitChanges(Product product) {
        publisher.publishEvent(new ProductEventChange(product, previous));
        previous = product;
    }
}

