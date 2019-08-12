package com.mashangshouche.car.controller.vo;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class CbsOrderVO {
    @NotBlank
    @ApiModelProperty("车辆ID")
    private String carId;
    @NotBlank
    @ApiModelProperty("联系人Name")
    private String name;
    @NotBlank
    @ApiModelProperty("联系人电话")
    private String phone;
    @NotBlank
    @ApiModelProperty("地址")
    private String address;
    @NotBlank
    @ApiModelProperty("城市ID")
    private String cityId;
    @NotBlank
    @ApiModelProperty("产品类型")
    private String type;
}
