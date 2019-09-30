package com.mashangshouche.car.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashangshouche.car.exception.BaseException;
import com.mashangshouche.car.exception.InternalServerErrorException;

import java.io.IOException;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class JacksonUtils {
    private static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
    }

    public static <T> T readValue(String content, Class<T> valueType) {
        try {
            return mapper.readValue(content, valueType);
        } catch (IOException e) {
            log.error("data parse error, content = {}", content, e);
            throw new InternalServerErrorException(content);
        }
    }

    public static <T> T readValue(String content, TypeReference valueTypeRef) {
        try {
            return mapper.readValue(content, valueTypeRef);
        } catch (IOException e) {
            log.error("data parse error, content = {}", content, e);
            throw new InternalServerErrorException(content);
        }
    }

    public static String writeValueAsString(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("write value error, object = {}", object, e);
            throw new InternalServerErrorException();
        }
    }
}
