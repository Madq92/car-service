package com.mashangshouche.car.repository;

import com.mashangshouche.car.entity.Car;
import com.mashangshouche.car.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CarRepository extends JpaRepository<Car, String> {
    Optional<Car> findByVin(String vin);

    Optional<Car> findByOrderId(String orderId);
}
