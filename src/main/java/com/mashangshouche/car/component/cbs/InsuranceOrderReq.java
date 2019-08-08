package com.mashangshouche.car.component.cbs;

import lombok.Data;

@Data
public class InsuranceOrderReq {
    private String name;
    private String phone;
    private String cityid;
    private String address;
    private String callbackurl;
    private String carno;
    private String type;
    private String vin;
}
