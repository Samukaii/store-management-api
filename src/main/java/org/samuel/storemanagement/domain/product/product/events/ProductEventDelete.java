package org.samuel.storemanagement.domain.product.product.events;

import lombok.Getter;
import lombok.Setter;
import org.samuel.storemanagement.domain.product.product.models.Product;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class ProductEventDelete extends ApplicationEvent {
    Product previous;
    Product current;

    public ProductEventDelete(Product current, Product previous) {
        super(current);
        this.previous = previous;
        this.current = current;
    }
}

