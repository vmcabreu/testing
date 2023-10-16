package com.crai.starter.jpa.exception;

public class DataExistException extends RuntimeException {

  public DataExistException() {
    super();
  }

  public DataExistException(String message) {
    super(message);
  }

  public DataExistException(String message, Throwable cause) {
    super(message, cause);
  }

  public DataExistException(Throwable cause) {
    super(cause);
  }
}
