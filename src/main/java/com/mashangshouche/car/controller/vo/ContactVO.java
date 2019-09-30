package com.mashangshouche.car.controller.vo;

import com.mashangshouche.car.controller.vo.base.InputConverter;
import com.mashangshouche.car.controller.vo.base.OutputConverter;
import com.mashangshouche.car.entity.Contact;

import javax.persistence.Entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class ContactVO implements OutputConverter<ContactVO, Contact>, InputConverter<Contact> {

    @ApiModelProperty("ID")
    private String id;
    @ApiModelProperty("城市ID")
    private String cityId;
    @ApiModelProperty("城市Name")
    private String cityName;
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("电话号码")
    private String phone;
    @ApiModelProperty("地址")
    private String address;
}


