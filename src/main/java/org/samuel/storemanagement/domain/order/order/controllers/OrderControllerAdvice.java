package org.samuel.storemanagement.domain.order.order.controllers;

import org.samuel.storemanagement.domain.order.order.exceptions.OrderNotFoundException;
import org.samuel.storemanagement.general.dto.ApplicationError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class OrderControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Object> catchNullError(OrderNotFoundException error) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApplicationError(error.getMessage()));
    }
}
