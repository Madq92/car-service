package com.mashangshouche.car.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public abstract class BaseException extends RuntimeException {
    private static final String SYSTEM_ERROR = "system error!";
    private Object errorData;

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    @NonNull
    public abstract HttpStatus getStatus();

    @Nullable
    public Object getErrorData() {
        return errorData;
    }

    @NonNull
    public BaseException setErrorData(@Nullable Object errorData) {
        this.errorData = errorData;
        return this;
    }
}
