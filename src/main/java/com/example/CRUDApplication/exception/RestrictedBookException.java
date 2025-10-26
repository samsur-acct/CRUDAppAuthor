package com.example.CRUDApplication.exception;

public class RestrictedBookException extends RuntimeException {
  public RestrictedBookException(String message) {
    super(message);
  }
}
