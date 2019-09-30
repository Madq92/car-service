package com.mashangshouche.car.controller;

import com.mashangshouche.car.common.IgnoreLogin;
import com.mashangshouche.car.common.Result;
import com.mashangshouche.car.controller.vo.CarVO;
import com.mashangshouche.car.entity.Car;
import com.mashangshouche.car.service.CarService;

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

@Api(description = "不需要登录")
@IgnoreLogin
@RestController
@RequestMapping("/car")
public class CarController {
    @Autowired
    CarService carService;

    @ApiOperation("车辆列表")
    @GetMapping
    public Result<Page<CarVO>> list(@RequestParam(value = "vin",required = false) @ApiParam("VIN码") String vin,
                                    @RequestParam(value = "level", required = false) @ApiParam("等级") String level,
                                    @RequestParam("page") @ApiParam("page") Integer page,
                                    @RequestParam("pageSize") @ApiParam("pageSize") Integer pageSize) {
        Page<Car> carPage = carService.searchCar(Car.Status.ON_SALE, null, vin, level, page, pageSize);
        return Result.ok(carPage.map(CarVO::customConverter));
    }


    @ApiOperation("车辆详情")
    @GetMapping("/{id}")
    public Result<CarVO> get(@PathVariable("id") @ApiParam("ID") String id) {
        return Result.ok(new CarVO().convertFrom(carService.getCar(id)));
    }
}
