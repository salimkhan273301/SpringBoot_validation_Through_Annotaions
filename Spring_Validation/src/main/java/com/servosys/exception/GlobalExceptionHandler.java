/*
 * package com.servosys.exception;
 * 
 * 
 * 
 * import java.util.HashMap; import java.util.Map;
 * 
 * import org.springframework.http.*; import
 * org.springframework.web.bind.MethodArgumentNotValidException; import
 * org.springframework.web.bind.annotation.*;
 * 
 * @RestControllerAdvice public class GlobalExceptionHandler {
 * 
 * @ExceptionHandler(ResourceNotFoundException.class) public
 * ResponseEntity<String> handleNotFound(ResourceNotFoundException ex) { return
 * ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage()); }
 * 
 * @ExceptionHandler(DuplicateResourceException.class) public
 * ResponseEntity<String> handleDuplicate(DuplicateResourceException ex) {
 * return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage()); }
 * 
 * @ExceptionHandler(MethodArgumentNotValidException.class) public
 * ResponseEntity<Map<String, String>> handleValidation(
 * MethodArgumentNotValidException ex) {
 * 
 * Map<String, String> errors = new HashMap<>();
 * 
 * ex.getBindingResult().getFieldErrors() .forEach(err ->
 * errors.put(err.getField(), err.getDefaultMessage()));
 * 
 * return ResponseEntity.badRequest().body(errors); } }
 */


package com.servosys.exception;

import com.servosys.dto.ApiResponse;
import com.servosys.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleResourceNotFoundException(
            ResourceNotFoundException ex) {
        log.error("Resource not found: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(ex.getMessage(), HttpStatus.NOT_FOUND.value()));
    }
    
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationException(
            ValidationException ex) {
        log.error("Validation error: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Validation failed",
                request.getDescription(false)
        );
        errorResponse.setValidationErrors(errors);
        
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGlobalException(
            Exception ex, WebRequest request) {
        log.error("Unexpected error occurred: {}", ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(
                    "An unexpected error occurred: " + ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
                ));
    }
}