package com.mashangshouche.car.service;

import com.mashangshouche.car.common.BaseService;
import com.mashangshouche.car.entity.Car;
import com.mashangshouche.car.exception.NotFindException;
import com.mashangshouche.car.repository.CarRepository;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

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

    public Optional<Car> findByOrderId(String orderId) {
        return carRepository.findByOrderId(orderId);
    }

    /**
     * 获取车辆，carID不存在会报错
     *
     * @param carId carId
     * @return car
     */
    public Car getCar(String carId) {
        Optional<Car> carOptional = carRepository.findById(carId);
        if (!carOptional.isPresent()) {
            throw new NotFindException("车辆未找到");
        }
        return carOptional.get();
    }

    public Page<Car> searchCar(Car.Status carStatus, Car.OrderStatus orderStatus, String vin, String level,
                               Integer page, Integer pageSize) {
        Car car = new Car();
        if (carStatus != null) {
            car.setStatus(Car.Status.ON_SALE);
        }
        if (orderStatus != null){
            car.setOrderStatus(orderStatus);
        }
        if (StringUtils.isNotEmpty(vin)) {
            car.setVin(vin);
        }
        if (StringUtils.isNotEmpty(level)) {
            car.setCarLevel(level);
        }
        Example<Car> carExample = Example.of(car);
        Page<Car> carPage = carRepository.findAll(carExample, PageRequest.of(page, pageSize,
                Sort.by(Sort.Direction.DESC, "createTime")));
        return carPage;
    }
}
