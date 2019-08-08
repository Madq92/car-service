package com.mashangshouche.car.entity;

import com.mashangshouche.car.common.BaseEntity;

import javax.persistence.Entity;

import lombok.Data;

@Entity
@Data
public class User extends BaseEntity {
    private String name;
    private String password;
    private String phone;
    private String avatar;
}
