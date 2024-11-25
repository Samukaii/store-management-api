package org.samuel.storemanagement.domain.preparation.preparation.controllers;

import org.samuel.storemanagement.domain.rawMaterial.rawMaterial.exceptions.RawMaterialNotFoundException;
import org.samuel.storemanagement.domain.rawMaterial.rawMaterial.exceptions.RawMaterialRequiredFieldNotReceivedException;
import org.samuel.storemanagement.general.dto.ApplicationError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class PreparationControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RawMaterialNotFoundException.class)
    public ResponseEntity<Object> catchNullError(RawMaterialNotFoundException error) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApplicationError(error.getMessage()));
    }

    @ExceptionHandler(RawMaterialRequiredFieldNotReceivedException.class)
    public ResponseEntity<Object> catchRequiredFieldError(RawMaterialRequiredFieldNotReceivedException error) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new ApplicationError(error.getMessage()));
    }
}
