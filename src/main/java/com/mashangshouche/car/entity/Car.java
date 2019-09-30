package com.mashangshouche.car.entity;

import com.mashangshouche.car.common.BaseEntity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.Getter;

@Entity
@Data
public class Car extends BaseEntity {

    private String carLevel;
    private String carNo;
    private String leftPicture;

    private String modelName;
    private String plateYear;
    private String vin;
    private String watchMile;
    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private Contact contact;

    private String orderId;
    private String reportUrl;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @Enumerated(EnumType.STRING)
    private Status status;
    private Long price;
    private Date orderEndTime;

    @Getter
    public enum OrderStatus {
        WAITING_FOR_CHECK("待检测"),
        SUCCESS("检测成功");

        private String display;

        OrderStatus(String display) {
            this.display = display;
        }

        public static OrderStatus getByName(String name){
            for (OrderStatus orderStatus :values()){
                if (orderStatus.name().equals(name)){
                    return orderStatus;
                }
            }
            return null;
        }
    }

    @Getter
    public enum Status{
        OFF_SHELF("待上架"),
        ON_SALE("在售"),
        SOLD("已售");

        private String display;

        Status(String display) {
            this.display = display;
        }

        public static Status getByName(String name){
            for (Status status :values()){
                if (status.name().equals(name)){
                    return status;
                }
            }
            return null;
        }
    }
}
