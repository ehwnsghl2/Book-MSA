package com.brandjunhoe.rental.rental.application;

/**
 * Create by DJH on 2021/09/30.
 */
public class RentUnavailableException extends RuntimeException {
    public RentUnavailableException(String message) {
        super(message);
    }
}
