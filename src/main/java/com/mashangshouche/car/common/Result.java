package com.mashangshouche.car.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Result<T> {
    private int code;
    private String msg;
    private T body;

    public static <T> Result<T> ok() {
        return new Result(0, "OK", null);
    }

    public static <T> Result<T> ok(T body) {
        return new Result(0, "OK", body);
    }

    public static <T> Result<T> error(int code, String msg) {
        return new Result(code, msg, null);
    }
}
