package com.mashangshouche.car.repository;

import com.mashangshouche.car.entity.Car;
import com.mashangshouche.car.entity.Contact;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ContactRepository extends JpaRepository<Contact, String> {
    Optional<Contact> findByPhone(String phone);

    Optional<Contact> findByCityId(String cityid);

    Optional<Contact> findByName(String name);
}
