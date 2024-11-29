package org.samuel.storemanagement.domain.rawMaterial.category.events;

import lombok.Getter;
import lombok.Setter;
import org.samuel.storemanagement.domain.rawMaterial.category.models.RawMaterialCategory;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class RawMaterialCategoryPersistEvent extends ApplicationEvent {
    private RawMaterialCategory category;

    public RawMaterialCategoryPersistEvent(RawMaterialCategory event) {
        super(event);
        category = event;
    }
}
