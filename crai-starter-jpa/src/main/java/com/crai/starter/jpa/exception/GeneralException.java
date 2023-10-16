package com.crai.starter.jpa.exception;

public class GeneralException extends RuntimeException {

  public GeneralException() {
    super();
  }

  public GeneralException(String message) {
    super(message);
  }

  public GeneralException(String message, Throwable cause) {
    super(message, cause);
  }

  public GeneralException(Throwable cause) {
    super(cause);
  }
}
