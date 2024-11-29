package org.samuel.storemanagement.domain.rawMaterial.rawMaterial.events;

import lombok.Getter;
import lombok.Setter;
import org.samuel.storemanagement.domain.rawMaterial.rawMaterial.models.RawMaterial;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class RawMaterialPersistEvent extends ApplicationEvent {
    private RawMaterial rawMaterial;

    public RawMaterialPersistEvent(RawMaterial event) {
        super(event);
        rawMaterial = event;
    }
}
