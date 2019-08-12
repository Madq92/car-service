package com.mashangshouche.car.controller;

import com.google.common.collect.Maps;

import com.mashangshouche.car.common.Result;
import com.mashangshouche.car.component.cbs.CbsHelper;
import com.mashangshouche.car.component.cbs.OpenCityResp;
import com.mashangshouche.car.controller.vo.CbsOrderVO;
import com.mashangshouche.car.entity.Car;
import com.mashangshouche.car.exception.BaseException;
import com.mashangshouche.car.exception.NotFindException;
import com.mashangshouche.car.service.CarService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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
    public Result<Boolean> add(@RequestBody @Validated CbsOrderVO orderVO) {
        Optional<Car> carOptional = carService.findById(orderVO.getCarId());
        if (!carOptional.isPresent()) {
            throw new NotFindException();
        }
        Car car = carOptional.get();
        if (StringUtils.isNotEmpty(car.getOrderNo())) {
            return Result.ok(true);
        }

        HashMap<String, Object> params = Maps.newHashMap();
        params.put("name", orderVO.getName());
        params.put("phone", orderVO.getPhone());

        params.put("cityid", orderVO.getCityId());
        params.put("address", orderVO.getAddress());

        params.put("type", orderVO.getType());

        params.put("callbackurl", cbsCallbackUrl);

        if (StringUtils.isNotEmpty(car.getLicensePlate())) {
            params.put("carno", car.getLicensePlate());
        }
        if (StringUtils.isNotEmpty(car.getVin())) {
            params.put("vin", car.getVin());
        }
        String orderId = cbsHelper.insuranceOrder(params);
        car.setOrderNo(orderId);
        car.setOrderStatus(Car.OrderStatus.WAITING_FOR_CHECK);
        carService.save(car);
        return Result.ok(true);
    }

    @ApiOperation("获取开通城市")
    @GetMapping("/openCity")
    public Result<List<OpenCityResp>> add() {
        return Result.ok(cbsHelper.opencity());
    }

    @ApiOperation("订单状态")
    @GetMapping("/synOrder/{carId}")
    public Result<Boolean> orderInfo(@PathVariable("carId") @ApiParam("车辆ID") String carId) {
        Optional<Car> carOptional = carService.findById(carId);
        if (!carOptional.isPresent()) {
            throw new NotFindException("车辆为找到");
        }
        Car car = carOptional.get();
        if (StringUtils.isEmpty(car.getOrderNo())) {
            throw new BaseException("订单还未创建");
        }
        if (cbsHelper.insuranceOrderinfo(car.getOrderNo())){
            car.setOrderStatus(Car.OrderStatus.SUCCESS);
            carService.save(car);
        }
        return Result.ok(true);
    }


    @ApiOperation("获取报告URL")
    @GetMapping("/reportUrl/{carId}")
    public Result<String> reportUrl(@PathVariable("carId") @ApiParam("车辆ID") String carId) {
        Optional<Car> carOptional = carService.findById(carId);
        if (!carOptional.isPresent()) {
            throw new NotFindException("车辆未找到");
        }
        Car car = carOptional.get();
        if (StringUtils.isEmpty(car.getOrderNo())) {
            throw new BaseException("订单还未创建");
        }
        return Result.ok(cbsHelper.getReportUrl(car.getOrderNo()));
    }
}
