package com.breadcrumbdata.locationengineserver.util;

import com.breadcrumbdata.locationengineserver.config.CustomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        CustomResponse customResponse = new CustomResponse();
        customResponse.setCode(HttpStatus.BAD_REQUEST.value());
        customResponse.setMsg(ex.getFieldError().getDefaultMessage());
        customResponse.setData(null);
        return ResponseEntity.status(400).body(customResponse);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<?> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex, HttpServletRequest request) {
        CustomResponse customResponse = new CustomResponse();
        customResponse.setCode(HttpStatus.BAD_REQUEST.value());
        customResponse.setMsg(ex.getMessage());
        customResponse.setData(null);
        return ResponseEntity.status(400).body(customResponse);
    }
}
