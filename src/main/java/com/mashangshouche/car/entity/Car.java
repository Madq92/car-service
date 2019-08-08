package com.mashangshouche.car.entity;

import com.mashangshouche.car.common.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.Data;
import lombok.Getter;

@Entity
@Data
public class Car extends BaseEntity {
    private String cityid;
    private String cityName;
    private String userName;
    private String userPhone;
    private String address;
    private String licensePlate;
    private String type;
    private String vin;

    private String model;
    private Long price;

    private String orderNo;
    private String reportUrl;

    @Enumerated(EnumType.STRING)
    private OrderType orderType;


    @Getter
    public enum OrderType {
        WAITING_FOR_CHECK("待检测"),
        SUCCESS("检测成功");

        private String display;

        OrderType(String display) {
            this.display = display;
        }
    }
}
