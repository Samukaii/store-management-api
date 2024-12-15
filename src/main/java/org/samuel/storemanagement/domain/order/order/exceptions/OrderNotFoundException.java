package org.samuel.storemanagement.domain.order.order.exceptions;

import org.samuel.storemanagement.general.exceptions.ResourceNotFoundException;

public class OrderNotFoundException extends ResourceNotFoundException {
    public OrderNotFoundException() {
        super("Insumo não encontrado!");
    }
}
