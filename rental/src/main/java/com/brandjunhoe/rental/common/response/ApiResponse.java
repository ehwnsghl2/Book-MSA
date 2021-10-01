package com.brandjunhoe.rental.common.response;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@ToString
@Getter
public class ApiResponse<T> {
    
    private int status;
    private String message;
    private T data;
    
    public ApiResponse() {
        this.status = HttpStatus.OK.value();
        this.message = HttpStatus.OK.getReasonPhrase();
    }

    public ApiResponse(HttpStatus httpStatus) {
        this.status = httpStatus.value();
        this.message = httpStatus.getReasonPhrase();
    }

    public ApiResponse(HttpStatus httpStatus, String message) {
        this.status = httpStatus.value();
        this.message = message;
    }

    public ApiResponse(String message) {
        this.status = HttpStatus.OK.value();
        this.message = message;
    }
    
    public ApiResponse(T data) {
        this.status = HttpStatus.OK.value();
        this.message = HttpStatus.OK.getReasonPhrase();
        this.data = data;
    }
    
}
