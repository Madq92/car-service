package com.mashangshouche.car.controller.vo;

import com.mashangshouche.car.entity.User;
import com.mashangshouche.car.util.BeanCopyUtils;

import lombok.Data;

@Data
public class UserVO {
    private String uuid;
    private String token;
    private String name;
    private String phone;
    private String avatar;

    public UserVO of(User user) {
        BeanCopyUtils.copy(user, this);
        return this;
    }

    public User to() {
        User user = new User();
        BeanCopyUtils.copy(this, user);
        return user;
    }
}
