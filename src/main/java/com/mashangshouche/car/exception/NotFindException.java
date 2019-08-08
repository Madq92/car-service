package com.mashangshouche.car.exception;

import org.springframework.http.HttpStatus;

public class NotFindException extends BaseException {
    private static final String PARAMETER_ERROR = "Not Found";

    public NotFindException() {
        super(HttpStatus.NOT_FOUND.value());
    }

    public NotFindException(String errorMessage) {
        super(HttpStatus.NOT_FOUND.value(), errorMessage);
    }
}
