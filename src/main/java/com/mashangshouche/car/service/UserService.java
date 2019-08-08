package com.mashangshouche.car.service;

import com.mashangshouche.car.common.BaseService;
import com.mashangshouche.car.entity.User;
import com.mashangshouche.car.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserService implements BaseService<User, String> {
    @Autowired
    UserRepository userRepository;

    @Override
    public JpaRepository<User, String> getRepository() {
        return userRepository;
    }

    public Optional<User> findByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }

}
