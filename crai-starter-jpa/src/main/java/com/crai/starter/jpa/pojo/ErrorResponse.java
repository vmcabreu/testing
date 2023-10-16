package com.crai.starter.jpa.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.List;
import java.util.TimeZone;

@Data
public class ErrorResponse {
  private HttpStatus status;
  private ErrorCodeEnum errorCode;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss", timezone = "UTC")
  private Instant timestamp;
  private String message;
  @JsonIgnore
  private String debugMessage;
  private List<DefaultErrorSubResponse> subErrors;

  public ErrorResponse() {
    timestamp = Instant.now();
    errorCode = ErrorCodeEnum.ERR_DEFAULT_CODE;
  }

  public ErrorResponse(HttpStatus status, ErrorCodeEnum errorCode) {
    this();
    this.status = status;
    this.errorCode = errorCode;
  }

  public ErrorResponse(HttpStatus status, ErrorCodeEnum errorCode, Throwable ex) {
    this();
    this.status = status;
    this.errorCode = errorCode;
    this.message = "Unexpected error";
    this.debugMessage = ex.getMessage();
  }

  public ErrorResponse(HttpStatus status, String message, ErrorCodeEnum errorCode, Throwable ex) {
    this();
    this.status = status;
    this.message = message;
    this.errorCode = errorCode;
    this.debugMessage = ex.getMessage();
  }
}
