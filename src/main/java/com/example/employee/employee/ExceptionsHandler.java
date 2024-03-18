package com.example.employee.employee;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseBody
public class ExceptionsHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({UserException.class})
    public ResponseEntity<Object> notFound(UserException e){
        Error error = new Error();
        error.setErrorCode(e.getErrorCode());
        error.setErrorMessage(e.getMessage());
        return ResponseEntity.ok(error);
    }
}
