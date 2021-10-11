package com.brandjunhoe.user.common.exception;

public class DataNotFoundException extends RuntimeException{
    public DataNotFoundException(String message) {
        super(message);
    }
}