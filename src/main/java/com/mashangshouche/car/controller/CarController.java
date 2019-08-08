package com.mashangshouche.car.controller;

import com.mashangshouche.car.common.Result;
import com.mashangshouche.car.controller.vo.CarVO;
import com.mashangshouche.car.entity.Car;
import com.mashangshouche.car.exception.NotFindException;
import com.mashangshouche.car.service.CarService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
@RequestMapping("/car")
public class CarController {
    @Autowired
    CarService carService;

    @ApiOperation("车辆列表")
    @GetMapping
    public Result<Page<CarVO>> list(@RequestParam(value = "vin",required = false) @ApiParam("VIN码") String vin,
                                    @RequestParam("page") @ApiParam("page") Integer page,
                                    @RequestParam("pageSize") @ApiParam("pageSize") Integer pageSize) {
        Car car = new Car();
        if (StringUtils.isNotEmpty(vin)) {
            car.setVin(vin);
        }
        Example<Car> userExample = Example.of(car);
        Page<Car> carPage = carService.findAll(userExample, PageRequest.of(page, pageSize,
                Sort.by(Sort.Direction.DESC, "createTime")));
        return Result.ok(carPage.map(CarVO::of));
    }

    @ApiOperation("车辆详情")
    @GetMapping("/{id}")
    public Result<CarVO> get(@PathVariable("id") @ApiParam("ID") String id) {
        Optional<Car> carOptional = carService.findById(id);
        if (!carOptional.isPresent()) {
            throw new NotFindException("车辆未找到");
        }
        return Result.ok(new CarVO().of(carOptional.get()));
    }

    @ApiOperation("添加车辆")
    @PostMapping
    public Result<CarVO> add(@RequestBody CarVO carVO) {
        carVO.setId(null);
        return Result.ok(CarVO.of(carService.save(carVO.to())));
    }

    @ApiOperation("修改车辆")
    @PutMapping("/{id}")
    public Result<CarVO> edit(@PathVariable("id") @ApiParam("ID") String id,
                              @RequestBody CarVO carVO) {
        Optional<Car> carOptional = carService.findById(id);
        if (!carOptional.isPresent()) {
            throw new NotFindException();
        }
        Car oldCar = carOptional.get();
        oldCar.setAddress(carVO.getAddress());
        Car updateCar = carService.update(oldCar);
        return Result.ok(CarVO.of(updateCar));
    }
}
