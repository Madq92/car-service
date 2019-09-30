package com.mashangshouche.car.configuration;


import com.mashangshouche.car.common.Result;
import com.mashangshouche.car.exception.BaseException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import lombok.extern.log4j.Log4j2;

@Log4j2
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<Result> handleException(Exception ex) {
        if (ex instanceof BaseException) {
            BaseException baseException = (BaseException) ex;
            log.error(baseException.getMessage(), baseException);
            Result baseErrorResult = Result.error(baseException.getStatus().value(), baseException.getMessage());
            return ResponseEntity.status(baseException.getStatus().value()).body(baseErrorResult);
        } else if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException validException = (MethodArgumentNotValidException) ex;
            log.error(validException.getMessage());
            BindingResult bindingResult = validException.getBindingResult();
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            Result errorResult = Result.error(400, errorMessage);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResult);
        } else {
            log.error(ex);
            Result errorResult = Result.error(500, ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResult);
        }
    }


}
