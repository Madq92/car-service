package com.mashangshouche.car.controller.vo;



import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel
public class LoginVO {
    @NotBlank(message = "电话号码不能为空")
    private String phone;
    @NotBlank(message = "密码不能为空")
    private String password;
}
