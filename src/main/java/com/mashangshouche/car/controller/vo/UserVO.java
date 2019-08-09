package com.mashangshouche.car.controller.vo;

import com.mashangshouche.car.entity.User;
import com.mashangshouche.car.util.BeanCopyUtils;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel
public class UserVO {
    private String id;
    private String token;
    @NotBlank(message = "用户名不能为空")
    private String name;
    @NotBlank(message = "电话号码不能为空")
    private String phone;
    private String avatar;

    public static UserVO of(User user) {
        UserVO vo = new UserVO();
        BeanCopyUtils.copy(user, vo);
        return vo;
    }

    public User to() {
        User user = new User();
        BeanCopyUtils.copy(this, user);
        return user;
    }
}
