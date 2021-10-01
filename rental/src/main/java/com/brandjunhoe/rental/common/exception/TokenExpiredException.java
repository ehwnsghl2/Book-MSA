package com.brandjunhoe.rental.common.exception;


public class TokenExpiredException extends RuntimeException {

    public TokenExpiredException() { super("ResponseMessage.FORBIDDEN_MSG"); }

}
