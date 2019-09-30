package com.mashangshouche.car.controller;

import com.google.common.collect.Maps;

import com.mashangshouche.car.common.IgnoreLogin;
import com.mashangshouche.car.common.Result;
import com.mashangshouche.car.component.cbs.CbsHelper;
import com.mashangshouche.car.component.cbs.OpenCityResp;
import com.mashangshouche.car.controller.vo.CbsCallbackVO;
import com.mashangshouche.car.controller.vo.CbsOrderVO;
import com.mashangshouche.car.entity.Car;
import com.mashangshouche.car.entity.Contact;
import com.mashangshouche.car.exception.BadRequestException;
import com.mashangshouche.car.exception.ParameterException;
import com.mashangshouche.car.repository.ContactRepository;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;

@Log4j2
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
    @Autowired
    ContactRepository contactRepository;

    @IgnoreLogin
    @ApiOperation("获取报告URL")
    @PostMapping("/callback")
    public Result<Void> callback(@RequestBody CbsCallbackVO vo) {
        log.info("CBS回调：" + vo.toString());
        String orderId = vo.getOrderid();
        Optional<Car> carOptional = carService.findByOrderId(orderId);
        if (!carOptional.isPresent()) {
            throw new ParameterException("没有此订单");
        }
        Car car = carOptional.get();
        car.setOrderStatus(Car.OrderStatus.SUCCESS);
        car.setReportUrl(cbsHelper.getReportUrl(orderId));
        carService.save(car);
        return Result.ok();
    }

    @ApiOperation("下单")
    @PostMapping("/order")
    public Result<Boolean> add(@RequestBody @Validated CbsOrderVO orderVO) {
        Optional<Contact> contactOptional = contactRepository.findById(orderVO.getContactId());
        if (!contactOptional.isPresent()) {
            throw new BadRequestException("联系人未找到");
        }
        Contact contact = contactOptional.get();

        HashMap<String, Object> params = Maps.newHashMap();
        params.put("name", contact.getName());
        params.put("phone", contact.getPhone());
        params.put("cityid", contact.getCityId());
        params.put("address", contact.getAddress());
        params.put("type", "101");
        params.put("callbackurl", cbsCallbackUrl);

        for (int i = 0; i < orderVO.getCarCount(); i++) {
            String orderId = cbsHelper.insuranceOrder(params);
            Car car = new Car();
            car.setOrderId(orderId);
            car.setOrderStatus(Car.OrderStatus.WAITING_FOR_CHECK);
            car.setContact(contact);
            carService.save(car);
        }

        return Result.ok(true);
    }

    @ApiOperation("获取开通城市")
    @GetMapping("/openCity")
    public Result<List<OpenCityResp>> add() {
        return Result.ok(cbsHelper.opencity());
    }

    @ApiOperation("订单状态")
    @GetMapping("/synOrder/{carId}")
    public Result<String> orderInfo(@PathVariable("carId") @ApiParam("车辆ID") String carId) {
        Car car = carService.getCar(carId);
        if (StringUtils.isEmpty(car.getOrderId())) {
            throw new BadRequestException("订单还未创建");
        }
        return Result.ok(cbsHelper.insuranceOrderinfo(car.getOrderId()));
    }

    @ApiOperation("获取报告URL")
    @GetMapping("/reportUrl/{carId}")
    public Result<String> reportUrl(@PathVariable("carId") @ApiParam("车辆ID") String carId) {
        Car car = carService.getCar(carId);
        if (StringUtils.isEmpty(car.getOrderId())) {
            throw new BadRequestException("订单还未创建");
        }
        return Result.ok(cbsHelper.getReportUrl(car.getOrderId()));
    }
}
