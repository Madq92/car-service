package com.mashangshouche.car.service;

import com.mashangshouche.car.common.BaseService;
import com.mashangshouche.car.entity.Car;
import com.mashangshouche.car.entity.User;
import com.mashangshouche.car.repository.CarRepository;
import com.mashangshouche.car.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import sun.tools.asm.CatchData;

import java.util.List;
import java.util.Optional;

@Component
public class CarService implements BaseService<Car, String> {
    @Autowired
    CarRepository carRepository;

    @Override
    public JpaRepository<Car, String> getRepository() {
        return carRepository;
    }

    public Optional<Car> findByVin(String vin) {
        return carRepository.findByVin(vin);
    }
}
