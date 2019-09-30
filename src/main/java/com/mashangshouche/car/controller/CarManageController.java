package com.mashangshouche.car.controller;

import com.mashangshouche.car.common.IgnoreLogin;
import com.mashangshouche.car.common.Result;
import com.mashangshouche.car.controller.vo.CarManageVO;
import com.mashangshouche.car.entity.Car;
import com.mashangshouche.car.service.CarService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api
@RestController
@RequestMapping("/carManage")
public class CarManageController {
    @Autowired
    CarService carService;

    @ApiOperation("车辆列表")
    @GetMapping
    public Result<Page<CarManageVO>> list(
            @RequestParam(value = "carStatus", required = false) @ApiParam("车辆状态") String carStatusStr,
            @RequestParam(value = "orderStatus", required = false) @ApiParam("查博士订单状态") String orderStatusStr,
            @RequestParam(value = "vin", required = false) @ApiParam("VIN码") String vin,
            @RequestParam(value = "level", required = false) @ApiParam("等级") String level,
            @RequestParam("page") @ApiParam("page") Integer page,
            @RequestParam("pageSize") @ApiParam("pageSize") Integer pageSize) {
        Car.Status carStatus = null;
        if (StringUtils.isEmpty(carStatusStr)) {
            carStatus = Car.Status.getByName(carStatusStr);
        }
        Car.OrderStatus orderStatus = null;
        if (StringUtils.isEmpty(orderStatusStr)) {
            orderStatus = Car.OrderStatus.getByName(orderStatusStr);
        }

        Page<Car> carPage = carService.searchCar(carStatus, orderStatus, vin, level, page, pageSize);
        return Result.ok(carPage.map(CarManageVO::customConverter));
    }


    @ApiOperation("车辆详情")
    @GetMapping("/{id}")
    public Result<CarManageVO> get(@PathVariable("id") @ApiParam("ID") String id) {
        return Result.ok(new CarManageVO().convertFrom(carService.getCar(id)));
    }
}
