package com.mashangshouche.car.controller;

import com.mashangshouche.car.common.Result;
import com.mashangshouche.car.controller.vo.UserVO;
import com.mashangshouche.car.entity.User;
import com.mashangshouche.car.exception.NotFindException;
import com.mashangshouche.car.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @ApiOperation("获取用户")
    @GetMapping(value = "/{id}")
    public Result<UserVO> get(@PathVariable("id") @ApiParam("ID") String id) {
        Optional<User> userOptional = userService.findById(id);
        if (!userOptional.isPresent()) {
            throw new NotFindException("用户未找到");
        }
        return Result.ok(new UserVO().of(userOptional.get()));
    }
}
