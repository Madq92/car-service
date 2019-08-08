package com.mashangshouche.car.controller.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel
public class LoginVO {
    private String phone;
    private String password;
}
