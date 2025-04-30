package com.acme.catalog.backend.config;

import com.acme.catalog.backend.dto.ErrorDto;
import com.acme.catalog.backend.exception.AppException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RestExceptionHandler {

  @ExceptionHandler(value = {AppException.class})
  @ResponseBody
  public ResponseEntity<ErrorDto> handleAppException(AppException ex) {
    return ResponseEntity.status(ex.getStatus()).body(new ErrorDto(ex.getMessage()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseBody
  public ResponseEntity<List<ErrorDto>> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException ex) {
    List<ErrorDto> errorList = new ArrayList<>();
    List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

    for (FieldError error : fieldErrors) {
      errorList.add(
          new ErrorDto(
              "Field: " + error.getField() + " - Error description: " + error.getDefaultMessage()));
    }

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorList);
  }
}
