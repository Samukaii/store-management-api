package org.samuel.storemanagement.domain.order.order.exceptions;

public class OrderNotFoundException extends Exception {
    public OrderNotFoundException() {
        super("Insumo não encontrado!");
    }
}
