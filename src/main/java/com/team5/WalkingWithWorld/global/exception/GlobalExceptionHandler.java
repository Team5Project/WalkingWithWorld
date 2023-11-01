package com.team5.WalkingWithWorld.global.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessLogicException.class)
    protected ResponseEntity handleBusinessLogicException(BusinessLogicException e){
        return new ResponseEntity<>(new BusinessLogicException(e.getMessage(),e.getExceptionCode()), HttpStatusCode.valueOf(e.getExceptionCode().getStatus()));
    }
}
