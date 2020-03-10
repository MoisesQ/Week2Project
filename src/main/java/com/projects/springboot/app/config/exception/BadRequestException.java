package com.projects.springboot.app.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

  public BadRequestException(String exception) {
    super(exception);
  }

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
}
