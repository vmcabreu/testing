package com.crai.platform.starter.event.exception;

public class EventsException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public EventsException(String mensaje) {
    super(mensaje);
  }

  public EventsException(String mensaje, Exception e) {
    super(mensaje, e);
  }

  public EventsException() {
    super();
  }

  public EventsException(String message, Throwable cause) {
    super(message, cause);
  }

  public EventsException(Throwable cause) {
    super(cause);
  }
}
