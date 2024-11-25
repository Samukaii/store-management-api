package org.samuel.storemanagement.domain.product.category.controllers;

import org.samuel.storemanagement.domain.product.category.exceptions.ProductCategoryNotFoundException;
import org.samuel.storemanagement.general.dto.ApplicationError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ProductCategoryControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ProductCategoryNotFoundException.class)
    public ResponseEntity<Object> catchNullError(ProductCategoryNotFoundException error) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApplicationError(error.getMessage()));
    }
}
