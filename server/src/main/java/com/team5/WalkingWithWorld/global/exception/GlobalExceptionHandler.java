package com.team5.WalkingWithWorld.global.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessLogicException.class)
    protected ResponseEntity handleBusinessLogicException(BusinessLogicException e){
        log.debug("로그 위치 확인 : {}",e.getExceptionCode());
        ErrorResponse response = ErrorResponse.of(e);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(e.getExceptionCode().getStatus()));
    }

//    @ExceptionHandler(EntityNotFoundException.class)
//    protected ResponseEntity handleEntityException(EntityNotFoundException e){
//        log.info("로그 위치 확인 : {}",e.getMessage());
//
//    }

}
