package com.team5.WalkingWithWorld.global.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private int status;
    private String message;
    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }
    public static ErrorResponse of(BusinessLogicException e) {
        return new ErrorResponse(e.getExceptionCode().getStatus(), e.getMessage());
    }
}

