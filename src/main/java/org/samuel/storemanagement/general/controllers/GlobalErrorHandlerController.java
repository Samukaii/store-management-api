package org.samuel.storemanagement.general.controllers;

import org.samuel.storemanagement.general.dto.ApplicationArgumentError;
import org.samuel.storemanagement.general.dto.ApplicationBasicError;
import org.samuel.storemanagement.general.dto.ApplicationErrorWrapper;
import org.samuel.storemanagement.general.exceptions.ConflictException;
import org.samuel.storemanagement.general.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalErrorHandlerController {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApplicationErrorWrapper> handleResourceNotFound(ResourceNotFoundException error) {
        ApplicationBasicError result = new ApplicationBasicError();
        ApplicationErrorWrapper wrapper = new ApplicationErrorWrapper(result);

        result.setMessage(error.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(wrapper);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApplicationErrorWrapper> handleResourceNotFound(ConflictException error) {
        ApplicationBasicError result = new ApplicationBasicError();
        ApplicationErrorWrapper wrapper = new ApplicationErrorWrapper(result);

        result.setMessage(error.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(wrapper);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApplicationErrorWrapper> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        ApplicationArgumentError result = new ApplicationArgumentError();
        ApplicationErrorWrapper wrapper = new ApplicationErrorWrapper(result);

        result.setMessages(errors);

        return ResponseEntity.unprocessableEntity().body(wrapper);
    }
}

