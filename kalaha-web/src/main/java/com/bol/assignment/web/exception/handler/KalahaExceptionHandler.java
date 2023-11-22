package com.bol.assignment.web.exception.handler;

import com.bol.assignment.web.exception.ExceptionResponse;
import com.bol.assignment.web.exception.KalahaException;
import com.bol.assignment.web.exception.KalahaIllegalMoveException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class KalahaExceptionHandler {

    @ExceptionHandler(KalahaException.class)
    public final ResponseEntity<ExceptionResponse> handleKalahaException(final KalahaException e){
        ExceptionResponse response = ExceptionResponse.of(e.getMessage(), HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
    @ExceptionHandler(KalahaIllegalMoveException.class)
    public final ResponseEntity<ExceptionResponse> handleIllegalMove(final KalahaIllegalMoveException e){
        ExceptionResponse response = ExceptionResponse.of(e.getMessage(), HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}