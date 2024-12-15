package org.samuel.storemanagement.domain.product.product.exceptions;

import org.samuel.storemanagement.general.exceptions.ResourceNotFoundException;

public class ProductNotFoundException extends ResourceNotFoundException {
    public ProductNotFoundException() {
        super("Produto não encontrado!");
    }
}
