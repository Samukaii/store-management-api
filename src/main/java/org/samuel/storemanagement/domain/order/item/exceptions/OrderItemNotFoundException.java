package org.samuel.storemanagement.domain.order.item.exceptions;

public class OrderItemNotFoundException extends Exception {
    public OrderItemNotFoundException() {
        super("Este ingrediente não foi cadastrado neste produto!");
    }
}
