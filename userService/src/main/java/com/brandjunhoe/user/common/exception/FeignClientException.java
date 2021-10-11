package com.brandjunhoe.user.common.exception;

import lombok.Getter;

import java.util.Collection;
import java.util.Map;

/**
 * Create by DJH on 2021/10/06.
 */
@Getter
public class FeignClientException extends RuntimeException {

    private final int status;
    private final String message;

    private final Map<String, Collection<String>> headers;

    public FeignClientException(Integer status, String message, Map<String, Collection<String>> headers) {
        super(message);
        this.status = status;
        this.message = message;
        this.headers = headers;

    }

}
