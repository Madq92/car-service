package com.mashangshouche.car.controller;

import com.google.common.collect.Maps;

import com.mashangshouche.car.common.Result;
import com.mashangshouche.car.component.cbs.CbsHelper;
import com.mashangshouche.car.component.cbs.OpenCityResp;
import com.mashangshouche.car.entity.Car;
import com.mashangshouche.car.exception.BaseException;
import com.mashangshouche.car.exception.NotFindException;
import com.mashangshouche.car.service.CarService;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.prefs.BackingStoreException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api
@RestController
@RequestMapping("/cbs")
public class CbsController {
    @Value("${cbs.callbackUrl}")
    String cbsCallbackUrl;
    @Autowired
    CbsHelper cbsHelper;
    @Autowired
    CarService carService;

    @ApiOperation("下单")
    @PostMapping("/order")
    public Result<Boolean> add(@RequestParam("carId") @ApiParam("车辆ID") String carId,
                               @RequestParam("name") @ApiParam("联系人Name") String name,
                               @RequestParam("phone") @ApiParam("联系人电话") String phone,
                               @RequestParam("address") @ApiParam("地址") String address,
                               @RequestParam("cityid") @ApiParam("城市ID") String cityid) {
        Optional<Car> carOptional = carService.findById(carId);
        if (!carOptional.isPresent()) {
            throw new NotFindException();
        }
        Car car = carOptional.get();
        if (StringUtils.isNotEmpty(car.getOrderNo())) {
            return Result.ok(true);
        }

        HashMap<String, Object> params = Maps.newHashMap();
        params.put("name", name);
        params.put("phone", phone);

        params.put("cityid", cityid);
        params.put("address", address);

        params.put("callbackurl", cbsCallbackUrl);

        if (StringUtils.isNotEmpty(car.getLicensePlate())) {
            params.put("carno", car.getLicensePlate());
        }
        if (StringUtils.isNotEmpty(car.getType())) {
            params.put("type", car.getType());
        }
        if (StringUtils.isNotEmpty(car.getVin())) {
            params.put("vin", car.getVin());
        }
        String orderId = cbsHelper.insuranceOrder(params);
        car.setOrderNo(orderId);
        car.setOrderType(Car.OrderType.WAITING_FOR_CHECK);
        carService.save(car);
        return Result.ok(true);
    }

    @ApiOperation("获取开通城市")
    @GetMapping("/openCity")
    public Result<List<OpenCityResp>> add() {
        return Result.ok(cbsHelper.opencity());
    }

    @ApiOperation("订单状态")
    @GetMapping("/synOrder")
    public Result<Boolean> orderInfo(@RequestParam("carId") @ApiParam("车辆ID") String carId) {
        Optional<Car> carOptional = carService.findById(carId);
        if (!carOptional.isPresent()) {
            throw new NotFindException();
        }
        Car car = carOptional.get();
        if (StringUtils.isEmpty(car.getOrderNo())) {
            throw new BaseException("订单还未创建");
        }
        if (cbsHelper.insuranceOrderinfo(car.getOrderNo())){
            car.setOrderType(Car.OrderType.SUCCESS);
            carService.save(car);
        }
        return Result.ok(true);
    }


    @ApiOperation("获取报告URL")
    @GetMapping("/reportUrl")
    public Result<String> reportUrl(@RequestParam("carId") @ApiParam("车辆ID") String carId) {
        Optional<Car> carOptional = carService.findById(carId);
        if (!carOptional.isPresent()) {
            throw new NotFindException();
        }
        Car car = carOptional.get();
        if (StringUtils.isEmpty(car.getOrderNo())) {
            throw new BaseException("订单还未创建");
        }
        return Result.ok(cbsHelper.getReportUrl(car.getOrderNo()));
    }
}
