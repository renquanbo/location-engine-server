package com.breadcrumbdata.locationengineserver.util;

import com.breadcrumbdata.locationengineserver.config.ErrorResponse;
import com.breadcrumbdata.locationengineserver.config.exceptions.*;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        return ResponseEntity.status(400).body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<?> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex, HttpServletRequest request) {
        return ResponseEntity.status(400).body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }

    @ExceptionHandler(UserNameNotFoundException.class)
    public ResponseEntity<?> handleUserNameNotFoundException(UserNameNotFoundException ex, HttpServletRequest request) {
        return ResponseEntity.status(400).body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }

    @ExceptionHandler(UnSupportedAuthenticationProtocolException.class)
    public ResponseEntity<?> handleUnSupportedAuthenticationProtocolException(UnSupportedAuthenticationProtocolException ex, HttpServletRequest request) {
        return ResponseEntity.status(400).body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException ex, HttpServletRequest request) {
        return ResponseEntity.status(401).body(new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), ex.getMessage()));
    }

    @ExceptionHandler(UserNameAlreadyExistsException.class)
    public ResponseEntity<?> handleUserNameAlreadyExistsException(UserNameAlreadyExistsException ex, HttpServletRequest request) {
        return ResponseEntity.status(400).body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<?> handleInvalidFormatException(InvalidFormatException ex, HttpServletRequest request) {
        if (ex.getMessage().contains("model.enums.Role")) {
            return ResponseEntity.status(400).body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "invalid role"));
        }
        return ResponseEntity.status(400).body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex, HttpServletRequest request) {
        String message = ex.getMessage();
        message = message.replace("com.breadcrumbdata.locationengineserver.model.", "");
        return ResponseEntity.status(400).body(new ErrorResponse((HttpStatus.BAD_REQUEST.value()), message));
    }

    @ExceptionHandler(LayerIdNotFoundException.class)
    public ResponseEntity<?> handleLayerIdNotFoundException(LayerIdNotFoundException ex, HttpServletRequest request) {
        String message = ex.getMessage();
        return ResponseEntity.status(400).body(new ErrorResponse((HttpStatus.BAD_REQUEST.value()), message));
    }

    @ExceptionHandler(AnchorIdOrNameAlreadyExistsException.class)
    public ResponseEntity<?> handleAnchorIdAlreadyExistsException(AnchorIdOrNameAlreadyExistsException ex, HttpServletRequest request) {
        String message = ex.getMessage();
        return ResponseEntity.status(400).body(new ErrorResponse((HttpStatus.BAD_REQUEST.value()), message));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex, HttpServletRequest request) {
        String message = ex.getMessage();
        ConstraintViolation<?> violation = ex.getConstraintViolations().iterator().next();
        if(violation != null) {
            return ResponseEntity.status(400).body(new ErrorResponse((HttpStatus.BAD_REQUEST.value()), violation.getPropertyPath().toString() + " " + violation.getMessage()));
        }
        return ResponseEntity.status(400).body(new ErrorResponse((HttpStatus.BAD_REQUEST.value()), message));
    }
}
