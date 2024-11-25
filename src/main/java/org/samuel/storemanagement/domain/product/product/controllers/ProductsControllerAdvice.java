package org.samuel.storemanagement.domain.product.product.controllers;

import org.samuel.storemanagement.domain.product.product.exceptions.ProductFieldNotReceivedException;
import org.samuel.storemanagement.domain.product.product.exceptions.ProductNotFoundException;
import org.samuel.storemanagement.general.dto.ApplicationError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ProductsControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Object> catchNullError(ProductNotFoundException error) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApplicationError(error.getMessage()));
    }

    @ExceptionHandler(ProductFieldNotReceivedException.class)
    public ResponseEntity<Object> catchRequiredFieldError(ProductFieldNotReceivedException error) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new ApplicationError(error.getMessage()));
    }
}
