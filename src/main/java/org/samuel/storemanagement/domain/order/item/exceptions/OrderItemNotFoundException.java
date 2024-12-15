package org.samuel.storemanagement.domain.order.item.exceptions;

import org.samuel.storemanagement.general.exceptions.ResourceNotFoundException;

public class OrderItemNotFoundException extends ResourceNotFoundException {
    public OrderItemNotFoundException() {
        super("Este ingrediente não foi cadastrado neste produto!");
    }
}
