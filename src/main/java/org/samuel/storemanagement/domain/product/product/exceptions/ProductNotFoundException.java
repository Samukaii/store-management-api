package org.samuel.storemanagement.domain.product.product.exceptions;

public class ProductNotFoundException extends Exception {
    public ProductNotFoundException() {
        super("Produto não encontrado!");
    }
}
