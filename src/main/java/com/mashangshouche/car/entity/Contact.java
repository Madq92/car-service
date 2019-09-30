package com.mashangshouche.car.entity;

import com.mashangshouche.car.common.BaseEntity;

import javax.persistence.Entity;

import lombok.Data;

@Entity
@Data
public class Contact extends BaseEntity {
    private String cityId;
    private String cityName;
    private String name;
    private String phone;
    private String address;
}
