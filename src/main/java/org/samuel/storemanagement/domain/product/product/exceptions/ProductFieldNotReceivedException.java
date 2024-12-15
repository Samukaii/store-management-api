package org.samuel.storemanagement.domain.product.product.exceptions;

import org.samuel.storemanagement.general.exceptions.ResourceNotFoundException;

public class ProductFieldNotReceivedException extends ResourceNotFoundException {
    public ProductFieldNotReceivedException(String fieldName) {
        super("O campo " + fieldName + " é obrigatório");
    }
}
