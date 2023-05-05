package com.portfolioCRUD.portfolio.advice;

import com.portfolioCRUD.portfolio.exception.ResourceNotFoundException;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorMessage> handleInvalidFormatException(HttpMessageNotReadableException ex) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), new Date(), "Bad Request", "The request body contains invalid format");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleValidationException(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<String> errors = fieldErrors.stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());
        ErrorMessage errorMessage = new ValidationErrorMessage(HttpStatus.BAD_REQUEST.value(), new Date(),
                "Bad Request", "The request body contains missing or invalid fields", errors);
        return ResponseEntity.badRequest().body(errorMessage);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(ResourceNotFoundException ex) {
        ErrorMessage errorMessage = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                "Recurso no encontrado",
                "El recurso con el ID " + ex.getMessage() + " no ha sido encontrado."
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

}
