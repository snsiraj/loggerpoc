package org.loggerpoc.app.customerproductmap;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(value=HttpStatus.NOT_FOUND, reason = "CUSTOMER_NOT_FOUND")
public class CustomerProductMapException extends RuntimeException {
  public CustomerProductMapException(String message) {
    super(message);
  }
}
