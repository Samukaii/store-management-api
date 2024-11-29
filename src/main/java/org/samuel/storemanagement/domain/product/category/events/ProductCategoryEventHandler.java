package org.samuel.storemanagement.domain.product.category.events;

import lombok.RequiredArgsConstructor;
import org.samuel.storemanagement.domain.product.category.models.ProductCategory;
import org.samuel.storemanagement.domain.product.category.services.ProductCategoryService;
import org.samuel.storemanagement.domain.product.product.events.ProductEventDelete;
import org.samuel.storemanagement.domain.product.product.events.ProductEventPostPersist;
import org.samuel.storemanagement.domain.product.product.events.ProductEventPrePersist;
import org.samuel.storemanagement.domain.product.product.models.Product;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ProductCategoryEventHandler {
    private final ProductCategoryService service;

    @EventListener
    @Async
    public void onProductChange(ProductEventPostPersist event) {
        Product previous = event.getPrevious();
        Product current = event.getCurrent();

        if(current != null && current.getCategory() != null)
            service.updateAssociation(current.getCategory());
        if(previous != null && previous.getCategory() != null)
            service.updateAssociation(previous.getCategory());
    }

    @EventListener
    @Async
    public void onProductChange(ProductEventPrePersist event) {
        var product = event.getProduct();

        if(product != null && product.getCategory() != null)
            service.updateAssociation(product.getCategory());
    }

    @EventListener
    @Async
    public void onProductDelete(ProductEventDelete event) {
        ProductCategory category = event.getCurrent().getCategory();

        if(category == null) return;

        service.updateAssociation(category);
    }
}
