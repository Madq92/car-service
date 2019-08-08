package com.mashangshouche.car.configuration;

import com.google.common.collect.Lists;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.mashangshouche.ca"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                ;
    }


    private List<ApiKey> securitySchemes() {
        return Lists.newArrayList(new ApiKey("token", "Authorization", "header"));
    }
}
