package com.mashangshouche.car.controller.vo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class CbsOrderVO {
    @NotBlank(message = "联系人ID不能为空")
    @ApiModelProperty("联系人ID")
    private String contactId;
    @NotNull(message = "车辆数不能为空")
    @Min(value = 1, message = "最少1辆车")
    @ApiModelProperty("车辆数量")
    private Integer carCount;
}
