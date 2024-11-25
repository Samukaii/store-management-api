package org.samuel.storemanagement.domain.product.product.exceptions;

public class ProductFieldNotReceivedException extends Exception {
    public ProductFieldNotReceivedException(String fieldName) {
        super("O campo " + fieldName + " é obrigatório");
    }
}
