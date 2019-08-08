package com.mashangshouche.car.configuration;

import com.mashangshouche.car.component.cbs.CbsHelper;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties("cbs")
public class ChaBoshiConfig {
    private String userId;
    private String keySecret;
    private Boolean onLine;

    @Bean
    public CbsHelper cbsBuilder(){
        return new CbsHelper(userId,keySecret,onLine);
    }
}
