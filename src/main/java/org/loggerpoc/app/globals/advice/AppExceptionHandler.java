package org.loggerpoc.app.globals.advice;

import lombok.extern.slf4j.Slf4j;
import org.loggerpoc.app.customer.CustomerNotFoundException;
import org.loggerpoc.app.customerproductmap.CustomerProductMapException;
import org.loggerpoc.app.product.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class AppExceptionHandler {

  @Value("${http.message.exception.customer.notfound}")
  private String customerNotFoundMessage;
  @ExceptionHandler(CustomerNotFoundException.class)
  public ResponseEntity<String> handleCustomerNotFoundException(CustomerNotFoundException e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(customerNotFoundMessage);
  }

  @Value("${http.message.exception.product.notfound}")
  private String productNotFoundMessage;
  @ExceptionHandler(ProductNotFoundException.class)
  public ResponseEntity<String> handleCustomerProductMapException(ProductNotFoundException e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(productNotFoundMessage);
  }

  @Value("${http.message.exception.customerproductmap.notpossible}")
  private String customerProductNotFoundMessage;
  @ExceptionHandler(CustomerProductMapException.class)
  public ResponseEntity<String> handleCustomerProductMapException(CustomerProductMapException e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(customerProductNotFoundMessage);
  }

}
