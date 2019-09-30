package com.mashangshouche.car.exception;

import org.springframework.http.HttpStatus;

public class InternalServerErrorException extends BaseException {
    private static final String ERROR_MESSAGE = "服务器内部错误";

    public InternalServerErrorException() {
        super(ERROR_MESSAGE);
    }

    public InternalServerErrorException(String message) {
        super(message);
    }

    public InternalServerErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
