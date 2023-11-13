package com.team5.WalkingWithWorld.global.exception;
import lombok.Getter;

public enum ExceptionCode {

    UNAUTHORIZED(401, "미인증 사용자 입니다."),
    WALKINGPATHS_NOT_FOUND(404,"산책로가 없습니다"),
    USER_NOT_FOUND(404,"사용자가 없습니다."),
    REVIEW_NOT_FOUND(404,"리뷰가 없습니다."),
    COMMENTS_NOT_FOUND(404,"댓글이 없습니다.");

    @Getter
    private  int status;

    @Getter
    private  String message;

    ExceptionCode(int status, String message){
        this.status = status;
        this.message = message;
    }
}