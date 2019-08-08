package com.mashangshouche.car.exception;

import lombok.Getter;

public class BaseException extends RuntimeException {
    private static final String SYSTEM_ERROR = "system error!";
    @Getter
    private int httpStatusCode;

    public BaseException() {
        super(SYSTEM_ERROR);
        this.httpStatusCode = 500;
    }

    public BaseException(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public BaseException(String message) {
        super(message);
        this.httpStatusCode = 500;
    }

    public BaseException(int httpStatusCode, String message) {
        super(message);
        this.httpStatusCode = httpStatusCode;
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(int httpStatusCode, String message, Throwable cause) {
        super(message, cause);
        this.httpStatusCode = httpStatusCode;
    }
}
