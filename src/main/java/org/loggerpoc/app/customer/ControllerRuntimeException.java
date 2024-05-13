package org.loggerpoc.app.customer;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR, reason = "Customer not found")
public class ControllerRuntimeException extends RuntimeException {

  public ControllerRuntimeException(String message) {
    super(message);
  }
}
