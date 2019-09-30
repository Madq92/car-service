package com.mashangshouche.car.configuration;

import com.mashangshouche.car.common.JwtHelper;
import com.mashangshouche.car.exception.InternalServerErrorException;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.UnsupportedEncodingException;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties("jwt")
public class JwtConfig {
    private String secret;

    @Bean
    public JwtHelper jwtHelper() {
        try {
            return new JwtHelper(secret);
        } catch (UnsupportedEncodingException e) {
            throw new InternalServerErrorException("jwt.secret error!");
        }
    }
}
