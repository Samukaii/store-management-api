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

    public void emitPrePersist(Product product) {
        publisher.publishEvent(new ProductEventPrePersist(product));
        previous = product;
    }

    public void emitPostPersist(Product product) {
        publisher.publishEvent(new ProductEventPostPersist(product, previous));
        previous = product;
    }

    public void emitDelete(Product product) {
        publisher.publishEvent(new ProductEventDelete(product, previous));
        previous = product;
    }
}

