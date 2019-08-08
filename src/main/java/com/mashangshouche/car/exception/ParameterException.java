package com.mashangshouche.car.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

public class ParameterException extends BaseException {
    private static final String PARAMETER_ERROR = "参数错误";
    @Getter
    private String message = PARAMETER_ERROR;

    public ParameterException() {
        super(HttpStatus.BAD_REQUEST.value());
    }

    public ParameterException(String... message) {
        super(400);
        if (null != message && message.length != 0) {
            StringBuffer sb = new StringBuffer(PARAMETER_ERROR).append(":");
            for (String msg : message) {
                sb.append(msg).append(",");
            }
            sb.setLength(sb.length() - 1);
            this.message = sb.toString();
        }
    }


}
