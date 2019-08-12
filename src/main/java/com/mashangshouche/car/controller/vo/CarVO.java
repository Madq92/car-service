package com.mashangshouche.car.controller.vo;

import com.mashangshouche.car.entity.Car;
import com.mashangshouche.car.util.BeanCopyUtils;
import com.mashangshouche.car.util.PriceUtils;

import javax.validation.constraints.Min;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class CarVO {
    @ApiModelProperty("车辆ID")
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
    @ApiModelProperty("订单号")
    private String orderNo;
    @ApiModelProperty("订单状态")
    private String orderStatus;
    @ApiModelProperty("订单状态str")
    private String orderStatusStr;
    @ApiModelProperty("报告URL")
    private String reportUrl;

    @ApiModelProperty("价格，单位万")
    public String getPriceWanStr() {
        if (this.price != null && this.price != 0) {
            return PriceUtils.toWanYuan(this.price);
        }
        return null;
    }


    public static CarVO of(Car car) {
        CarVO carVO = new CarVO();
        BeanCopyUtils.copy(car, carVO);
        if (car.getOrderStatus() != null) {
            carVO.setOrderStatus(car.getOrderStatus().name());
            carVO.setOrderStatusStr(car.getOrderStatus().getDisplay());
        }
        return carVO;
    }

    public Car to() {
        Car car = new Car();
        BeanCopyUtils.copy(this, car);
        return car;
    }
}
