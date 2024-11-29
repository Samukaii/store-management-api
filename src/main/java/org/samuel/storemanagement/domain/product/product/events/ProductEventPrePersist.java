package org.samuel.storemanagement.domain.product.product.events;

import lombok.Getter;
import lombok.Setter;
import org.samuel.storemanagement.domain.product.product.models.Product;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class ProductEventPrePersist extends ApplicationEvent {
    Product product;

    public ProductEventPrePersist(Product product) {
        super(product);
        this.product = product;
    }
}

