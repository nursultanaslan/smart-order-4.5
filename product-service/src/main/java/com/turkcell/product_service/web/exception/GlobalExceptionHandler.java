package com.turkcell.product_service.web.exception;

import com.turkcell.product_service.application.brand.exception.BrandNameAlreadyExistsException;
import com.turkcell.product_service.application.brand.exception.BrandNotFoundException;
import com.turkcell.product_service.application.category.exception.CategoryNameAlreadyExistsException;
import com.turkcell.product_service.application.category.exception.CategoryNotFoundException;
import com.turkcell.product_service.application.product.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BrandNotFoundException.class)
    public ResponseEntity<String> handleBrandNotFoundException(BrandNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<String> handleCategoryNotFoundException(CategoryNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotFoundException(ProductNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(BrandNameAlreadyExistsException.class)
    public ResponseEntity<String> handleAlreadyExistsException(BrandNameAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    @ExceptionHandler(CategoryNameAlreadyExistsException.class)
    public ResponseEntity<String> handleAlreadyExistsException(CategoryNameAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}
