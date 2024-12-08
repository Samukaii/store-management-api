package org.samuel.storemanagement.domain.preparation.preparation.events;

import lombok.Getter;
import lombok.Setter;
import org.samuel.storemanagement.domain.preparation.preparation.models.Preparation;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class PreparationChangeEvent extends ApplicationEvent {
    private Preparation preparation;

    public PreparationChangeEvent(Preparation event) {
        super(event);
        preparation = event;
    }
}
