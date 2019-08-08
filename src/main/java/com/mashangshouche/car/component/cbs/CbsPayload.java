package com.mashangshouche.car.component.cbs;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CbsPayload<T> {
    @JsonProperty("Code")
    private String code;

    @JsonProperty("Message")
    private String message;

    private T data;
}
