package org.samuel.storemanagement.domain.rawMaterial.category.events;

import lombok.RequiredArgsConstructor;
import org.samuel.storemanagement.domain.rawMaterial.category.models.RawMaterialCategory;
import org.samuel.storemanagement.domain.rawMaterial.category.services.RawMaterialCategoryService;
import org.samuel.storemanagement.domain.rawMaterial.rawMaterial.events.RawMaterialPersistEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RawMaterialCategoryEventHandler {
    private final RawMaterialCategoryService service;

    @EventListener
    @Async
    public void onRawMaterialChange(RawMaterialPersistEvent event) {
        RawMaterialCategory category = event.getRawMaterial().getCategory();

        if(category == null) return;

        service.updateAssociation(category, true);
    }
}
