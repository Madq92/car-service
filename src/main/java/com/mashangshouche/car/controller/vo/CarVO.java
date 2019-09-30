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
public class CarVO implements InputConverter<Car>, OutputConverter<CarVO, Car> {


    @ApiModelProperty("车辆ID")
    private String id;
    @ApiModelProperty("车辆评级")
    private String carLevel;
    @ApiModelProperty("车牌号")
    private String carNo;
    @ApiModelProperty("图片")
    private String leftPicture;
    @ApiModelProperty("车型")
    private String modelName;
    @ApiModelProperty("上牌时间")
    private String plateYear;
    @ApiModelProperty("VIN码")
    private String vin;
    @ApiModelProperty("里程")
    private String watchMile;


    @ApiModelProperty("自己的定价，分")
    private Long price;
    @ApiModelProperty("自己的定价，万")
    private String priceStr;

    @ApiModelProperty("车辆状态")
    private String status;
    @ApiModelProperty("车辆状态Str")
    private String statusStr;
    @ApiModelProperty("报告URL")
    private String reportUrl;

    public static CarVO customConverter(Car carModel) {
        CarVO carVO = new CarVO().convertFrom(carModel);
        Car.Status status = carModel.getStatus();
        if (status != null) {
            carVO.setStatus(status.name());
            carVO.setStatusStr(status.getDisplay());
        }

        Long price = carModel.getPrice();
        if (price != null) {
            carVO.setPrice(price);
            carVO.setPriceStr(PriceUtils.toWanYuan(price));
        }
        return carVO;
    }
}
