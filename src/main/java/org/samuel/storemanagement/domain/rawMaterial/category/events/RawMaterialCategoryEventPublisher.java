package org.samuel.storemanagement.domain.rawMaterial.category.events;

import lombok.RequiredArgsConstructor;
import org.samuel.storemanagement.domain.rawMaterial.category.models.RawMaterialCategory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RawMaterialCategoryEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public void emitChanges(RawMaterialCategory category) {
        applicationEventPublisher.publishEvent(new RawMaterialCategoryPersistEvent(category));
    }
}
