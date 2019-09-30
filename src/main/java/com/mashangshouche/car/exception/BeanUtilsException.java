package com.mashangshouche.car.exception;

public class BeanUtilsException extends InternalServerErrorException {
    private static final String BEAN_UTILS_ERROR = "BeanUtils Error";

    public BeanUtilsException() {
        super(BEAN_UTILS_ERROR);
    }

    public BeanUtilsException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
