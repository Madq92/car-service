package com.mashangshouche.car.controller.vo;

import com.mashangshouche.car.controller.vo.base.InputConverter;
import com.mashangshouche.car.controller.vo.base.OutputConverter;
import com.mashangshouche.car.entity.User;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel
public class UserVO implements InputConverter<User>, OutputConverter<UserVO,User> {
    private String id;
    private String token;
    @NotBlank(message = "用户名不能为空")
    private String name;
    @NotBlank(message = "电话号码不能为空")
    private String phone;
    private String avatar;
}
