package com.brandjunhoe.book.common.handler;

import com.brandjunhoe.book.common.exception.BadRequestException;
import com.brandjunhoe.book.common.exception.DataNotFoundException;
import com.brandjunhoe.book.common.exception.TokenExpiredException;
import com.brandjunhoe.book.common.exception.UnauthorizedException;
import com.brandjunhoe.book.common.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.BindException;
import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 잘못된 요청
     * HttpStatus 400
     */
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse BadRequestApiException(BadRequestException e) {
        return new ApiResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    /**
     * 인증 실패
     * HttpStatus 401
     */
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse UnauthorizedException() {
        return new ApiResponse(HttpStatus.UNAUTHORIZED);
    }

    /**
     * 권한 없음
     * HttpStatus 403
     */
    @ExceptionHandler({AccessDeniedException.class, TokenExpiredException.class})
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse AccessDeniedException() {
        return new ApiResponse(HttpStatus.FORBIDDEN);
    }

    /**
     * 허용되지 않는 방법(Request Method - GET, POST, PUT, DELETE)
     * HttpStatus 405
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ApiResponse HttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return new ApiResponse(HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * Validation 실패 (bindException)
     * HttpStatus 417
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse FormValidationException(BindException e) {
        return new ApiResponse(HttpStatus.EXPECTATION_FAILED, e.getMessage());
    }


    /**
     * 데이터 조회 실패 (데이터 조회 실패로 인한 처리 불가, 저장/수정/삭제 실패)
     * HttpStatus 204
     */
    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse NotFoundDataException(DataNotFoundException e) {
        return new ApiResponse(HttpStatus.NO_CONTENT, e.getMessage());
    }

    /**
     * 알수 없는 오류(내부 서버 오류)
     * httpStatus 500
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse handleRuntimeException(RuntimeException e) {
        log.info(e.getMessage());
        return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
