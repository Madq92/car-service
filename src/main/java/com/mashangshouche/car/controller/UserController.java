package com.mashangshouche.car.controller;

import com.mashangshouche.car.common.AuthHold;
import com.mashangshouche.car.common.Result;
import com.mashangshouche.car.controller.vo.UserVO;
import com.mashangshouche.car.entity.User;
import com.mashangshouche.car.exception.NotFindException;
import com.mashangshouche.car.service.UserService;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api
@RestController
@RequestMapping("/user")
public class UserController {
    private final String DEFAULT_PASSWORD = "123456";
    private final String DEFAULT_PASSWORD_MD5 = DigestUtils.md5Hex(DEFAULT_PASSWORD);
    @Autowired
    UserService userService;

    @ApiOperation("当前用户信息")
    @GetMapping("/my")
    public Result<UserVO> my() {
        User user = AuthHold.currentUser();
        return Result.ok(new UserVO().of(user));
    }

    @ApiOperation("获取用户")
    @GetMapping("/{id}")
    public Result<UserVO> get(@PathVariable("id") @ApiParam("ID") String id) {
        Optional<User> userOptional = userService.findById(id);
        if (!userOptional.isPresent()) {
            throw new NotFindException("用户未找到");
        }
        return Result.ok(new UserVO().of(userOptional.get()));
    }

    @ApiOperation("添加用户")
    @PostMapping
    public Result<UserVO> add(@Validated @RequestBody UserVO vo) {
        User user = new User();
        user.setName(vo.getName());
        user.setPhone(vo.getPhone());
        user.setAvatar(vo.getAvatar());
        user.setPassword(DEFAULT_PASSWORD_MD5);
        return Result.ok(UserVO.of(userService.save(user)));
    }

    @ApiOperation("用户列表")
    @GetMapping
    public Result<Page<UserVO>> list(@RequestParam(value = "phone",required = false) @ApiParam("电话号码") String phone,
                               @RequestParam(value = "name",required = false) @ApiParam("姓名") String name,
                              @RequestParam("page") @ApiParam("page") Integer page,
                              @RequestParam("pageSize") @ApiParam("pageSize") Integer pageSize) {
        User user = new User();
        if (StringUtils.isNotEmpty(phone)) {
            user.setPhone(phone);
        }
        if (StringUtils.isNotEmpty(name)){
            user.setName(name);
        }
        Example<User> userExample = Example.of(user);
        Page<User> carPage = userService.findAll(userExample, PageRequest.of(page, pageSize,
                Sort.by(Sort.Direction.DESC, "createTime")));
        return Result.ok(carPage.map(UserVO::of));
    }
}
