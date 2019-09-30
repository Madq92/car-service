package com.mashangshouche.car.exception;

public class CbsException extends InternalServerErrorException {
    public CbsException(String message) {
        super(message);
    }

    public CbsException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
