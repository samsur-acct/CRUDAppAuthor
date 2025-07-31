package com.example.CRUDApplication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {

  // Catch all uncaught exceptions
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex, WebRequest request) {
    ErrorResponse error = new ErrorResponse(ex.getMessage(), request.getDescription(false));
    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  // Handle specific: NoSuchElementException
  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<ErrorResponse> handleNoSuchElement(NoSuchElementException ex, WebRequest request) {
    ErrorResponse error = new ErrorResponse("Book not found", request.getDescription(false));
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }

  //Handle book that are restricted
  @ExceptionHandler(RestrictedBookException.class)
  public ResponseEntity<ErrorResponse> handleRestrictedBookException(RestrictedBookException ex, WebRequest request) {
    ErrorResponse error = new ErrorResponse(ex.getMessage(), request.getDescription(false));
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }
}
