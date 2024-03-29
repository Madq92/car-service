package com.mashangshouche.car.exception;

import org.springframework.http.HttpStatus;

public class NotFindException extends BaseException {
    public NotFindException(String message) {
        super(message);
    }

    public NotFindException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
