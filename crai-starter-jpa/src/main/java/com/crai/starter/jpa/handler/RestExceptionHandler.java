package com.crai.starter.jpa.handler;

import com.crai.starter.jpa.exception.DataException;
import com.crai.starter.jpa.exception.DataExistException;
import com.crai.starter.jpa.exception.GeneralException;
import com.crai.starter.jpa.exception.NoDataFoundException;
import com.crai.starter.jpa.pojo.ErrorCodeEnum;
import com.crai.starter.jpa.pojo.ErrorResponse;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.jpa.JpaSystemException;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.context.request.WebRequest;

import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@Slf4j
public class RestExceptionHandler  {

  @ExceptionHandler(value = {DataException.class})
  protected ResponseEntity<Object> handleDataException(
    RuntimeException ex, WebRequest request) {
    String bodyOfResponse = "Something wrong happened";
    ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST, bodyOfResponse, ErrorCodeEnum.ERR_DATA_CODE, ex);
    log.error(error.getDebugMessage(), ex);

    return buildResponseEntity(error);
  }

//  @ExceptionHandler(DataException.class)
//  public ResponseEntity<Object> handleDataException(ServerWebExchange exchange, DataException exc) {
//    exchange.getAttributes().putIfAbsent(ErrorAttributes.ERROR_ATTRIBUTE, exc);
//    String bodyOfResponse = "Something wrong happened";
//    ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST, bodyOfResponse, ErrorCodeEnum.ERR_DATA_CODE, exc);
//    return  buildResponseEntity(error);
////      exchange.getAttributes().putIfAbsent(ErrorAttributes.ERROR_ATTRIBUTE, exc);
////      return Rendering.view("errorView").modelAttribute("message", exc.getMessage()).build();
//    }


  @ExceptionHandler(value = {DataExistException.class})
  protected ResponseEntity<Object> handleDataExistException(
    RuntimeException ex, WebRequest request) {
    String bodyOfResponse = "Something wrong happened";
    ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST, bodyOfResponse, ErrorCodeEnum.ERR_DATAEXIST_CODE, ex);
    log.error(error.getDebugMessage(), ex);

    return buildResponseEntity(error);
  }

  @ExceptionHandler(value = {GeneralException.class})
  protected ResponseEntity<Object> handleGeneralException(
    RuntimeException ex, WebRequest request) {
    String bodyOfResponse = "Something wrong happened";
    ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST, bodyOfResponse, ErrorCodeEnum.ERR_SERVICE_CODE, ex);
    log.error(error.getDebugMessage(), ex);

    return buildResponseEntity(error);
  }

  @ExceptionHandler(value = {NoDataFoundException.class})
  protected ResponseEntity<Object> handleNoDataFoundException(NoDataFoundException ex,  WebRequest request){
    String bodyOfResponse = "Something wrong happened";
    ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND, bodyOfResponse, ErrorCodeEnum.ERR_SERVICE_CODE, ex);
    log.error(error.getDebugMessage(), ex);

    return buildResponseEntity(error);
  }

  @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
  protected ResponseEntity<Object> handleIllegalArgs(final RuntimeException ex, final WebRequest request) {
    String bodyOfResponse = "This should be application specific";
    ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST, bodyOfResponse, ErrorCodeEnum.ERR_INTEGRITY_CODE, ex);
    log.error(error.getDebugMessage(), ex);
    return buildResponseEntity(error);
  }

  /**
   * {@inheritDoc}
   */
  @ExceptionHandler(value = {MethodArgumentNotValidException.class})
  protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
    final HttpHeaders headers,
    final HttpStatus status,
    final WebRequest request) {

    /*
     * Se evita dar informacion al exterior del error producido. Ni campo ni mensaje informativo.
     * El error ya se traza en el log. El front debe disponer de control de validacion
     * de capos. La validacion en el back es para refuerzo y control de acceso ajeno al front.
     */
    final ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST, null, ErrorCodeEnum.ERR_VALIDATION_CODE, ex);
    log.error(error.getDebugMessage(), ex);
    return buildResponseEntity(error);
  }



  @ExceptionHandler(value = {HttpMessageNotReadableException.class})
  protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    String bodyOfResponse = "Malformed JSON request";
    ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST, bodyOfResponse, ErrorCodeEnum.ERR_MESSAGE_NOTREADABLE, ex);
    log.error(error.getDebugMessage(), ex);
    return buildResponseEntity(error);
  }

  private ResponseEntity<Object> buildResponseEntity(
    ErrorResponse errorResponse) {
    return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
  }

  @ExceptionHandler(value = {Exception.class})
  protected ResponseEntity<Object> handleGenericException(
    RuntimeException ex, WebRequest request) {
    String bodyOfResponse = "Something wrong happened";
    ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST, bodyOfResponse, ErrorCodeEnum.ERR_DEFAULT_CODE, ex);
    log.error(error.getDebugMessage(), ex);

    return buildResponseEntity(error);
  }

  @ExceptionHandler(value = {JpaSystemException.class})
  protected ResponseEntity<Object> jpaSystemExceptionException(
    RuntimeException ex, WebRequest request) {
    String bodyOfResponse = "Something wrong happened";
    ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST, bodyOfResponse, ErrorCodeEnum.ERR_DEFAULT_CODE, ex);
    log.error(error.getDebugMessage(), ex);

    return buildResponseEntity(error);
  }

  // other exception handlers below

} // Final RestExceptionHandler.
