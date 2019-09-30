package com.mashangshouche.car.exception;

public class ParameterException extends BadRequestException {
    private static final String PARAMETER_ERROR = "参数错误";
    public ParameterException() {
        super(PARAMETER_ERROR);
    }

    public ParameterException(String message) {
        super(message);
    }
}
