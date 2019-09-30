package com.mashangshouche.car.controller.vo;

import com.mashangshouche.car.controller.vo.base.InputConverter;
import com.mashangshouche.car.controller.vo.base.OutputConverter;
import com.mashangshouche.car.entity.Car;
import com.mashangshouche.car.util.DateUtils;
import com.mashangshouche.car.util.PriceUtils;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class CarManageVO extends CarVO implements InputConverter<Car>, OutputConverter<CarVO, Car> {
    @ApiModelProperty("订单号")
    private String orderId;
    @ApiModelProperty("订单状态")
    private String orderStatus;
    @ApiModelProperty("订单状态Str")
    private String orderStatusStr;
    @ApiModelProperty("车辆状态")
    private String status;
    @ApiModelProperty("车辆状态Str")
    private String statusStr;
    @ApiModelProperty("报告URL")
    private String reportUrl;

    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("查博士订单完成时间")
    private String orderEndTime;


    public static CarManageVO customConverter(Car carModel) {
        CarManageVO carManageVO = new CarManageVO();
        carManageVO.convertFrom(carModel);
        Car.Status status = carModel.getStatus();
        if (status != null) {
            carManageVO.setStatus(status.name());
            carManageVO.setStatusStr(status.getDisplay());
        }

        Long price = carModel.getPrice();
        if (price != null) {
            carManageVO.setPrice(price);
            carManageVO.setPriceStr(PriceUtils.toWanYuan(price));
        }
        Car.OrderStatus orderStatus = carModel.getOrderStatus();
        if (orderStatus != null) {
            carManageVO.setOrderStatus(orderStatus.name());
            carManageVO.setOrderStatusStr(orderStatus.getDisplay());
        }
        Date createTime = carModel.getCreateTime();
        if (createTime != null) {
            carManageVO.setCreateTime(DateUtils.format(createTime));
        }
        Date orderEndTime = carModel.getOrderEndTime();
        if (orderEndTime != null) {
            carManageVO.setOrderEndTime(DateUtils.format(orderEndTime));
        }
        return carManageVO;
    }
}
