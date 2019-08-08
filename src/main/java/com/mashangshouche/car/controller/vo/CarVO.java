package com.mashangshouche.car.controller.vo;

import com.mashangshouche.car.entity.Car;
import com.mashangshouche.car.util.BeanCopyUtils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class CarVO {
    @ApiModelProperty("C车辆ID")
    private String id;
    @ApiModelProperty("城市ID")
    private String cityid;
    @ApiModelProperty("城市Name")
    private String cityName;
    @ApiModelProperty("联系人Name")
    private String userName;
    @ApiModelProperty("联系人Phone")
    private String userPhone;
    @ApiModelProperty("地址")
    private String address;
    @ApiModelProperty("车牌号")
    private String licensePlate;
    @ApiModelProperty("类型(101：本地事故保 ，102：本地事故保)")
    private String type;
    @ApiModelProperty("vin码")
    private String vin;
    @ApiModelProperty("车型")
    private String model;
    @ApiModelProperty("价格，单位分")
    private Long price;
    @ApiModelProperty("报告URL")
    private String reportUrl;

    public static CarVO of(Car car) {
        CarVO carVO = new CarVO();
        BeanCopyUtils.copy(car, carVO);
        return carVO;
    }

    public Car to() {
        Car car = new Car();
        BeanCopyUtils.copy(this, car);
        return car;
    }
}
