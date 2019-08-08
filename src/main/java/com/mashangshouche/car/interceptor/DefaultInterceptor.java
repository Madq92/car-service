package com.mashangshouche.car.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashangshouche.car.common.Result;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class DefaultInterceptor {

    public void endResponse(HttpServletRequest request, HttpServletResponse response, Result<?> result) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonStr = objectMapper.writeValueAsString(result);
            response.setStatus(result.getCode());
            out = response.getWriter();
            out.write(jsonStr);
        } catch (IOException e) {
            log.error(e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
