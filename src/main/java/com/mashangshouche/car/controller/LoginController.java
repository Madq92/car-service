package com.mashangshouche.car.controller;


import com.mashangshouche.car.common.IgnoreLogin;
import com.mashangshouche.car.common.JwtHelper;
import com.mashangshouche.car.common.Result;
import com.mashangshouche.car.controller.vo.LoginVO;
import com.mashangshouche.car.controller.vo.UserVO;
import com.mashangshouche.car.entity.User;
import com.mashangshouche.car.exception.BaseException;
import com.mashangshouche.car.service.UserService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class LoginController {
    @Autowired
    JwtHelper jwtHelper;
    @Autowired
    UserService userService;

    @IgnoreLogin
    @ApiOperation("用户登录")
    @PutMapping(value = "/login")
    public Result<UserVO> login(@RequestBody LoginVO vo) {
        Optional<User> userOptional = userService.findByPhone(vo.getPhone());
        if (!userOptional.isPresent()) {
            throw new BaseException();
        }
        User user = userOptional.get();

        UserVO userVO = new UserVO().of(user);
        userVO.setToken(jwtHelper.sign(user.getId()));
        return Result.ok(userVO);
    }
}
