package org.samuel.storemanagement.domain.product.category.events;

import lombok.Getter;
import lombok.Setter;
import org.samuel.storemanagement.domain.product.category.models.ProductCategory;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class ProductCategoryChangeEvent extends ApplicationEvent {
    private ProductCategory category;

    public ProductCategoryChangeEvent(ProductCategory event) {
        super(event);
        category = event;
    }
}
