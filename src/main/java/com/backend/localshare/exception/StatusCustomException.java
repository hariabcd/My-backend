package com.backend.localshare.exception;

public class StatusCustomException extends RuntimeException {
    public StatusCustomException(String message) {
        super(message);
    }

  public StatusCustomException(String message, Throwable cause) {
    super(message, cause);
  }
}
