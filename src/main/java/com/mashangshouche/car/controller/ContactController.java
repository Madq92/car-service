package com.mashangshouche.car.controller;

import com.mashangshouche.car.common.Result;
import com.mashangshouche.car.controller.vo.ContactVO;
import com.mashangshouche.car.entity.Car;
import com.mashangshouche.car.entity.Contact;
import com.mashangshouche.car.exception.BadRequestException;
import com.mashangshouche.car.exception.BaseException;
import com.mashangshouche.car.repository.ContactRepository;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.Id;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api
@RestController
@RequestMapping("/contact")
public class ContactController {
    @Autowired
    ContactRepository contactRepository;

    @ApiOperation("联系人列表")
    @GetMapping
    public Result<List<ContactVO>> getAll() {
        List<Contact> contacts = contactRepository.findAll();
        if (contacts.isEmpty()) {
            throw new BadRequestException("没有联系人，请先添加联系人！");
        }
        Stream<ContactVO> objectStream = contacts.stream().map(contact -> new ContactVO().convertFrom(contact));
        List<ContactVO> collect = objectStream.collect(Collectors.toList());
        return Result.ok(collect);
    }

    @ApiOperation("添加联系人")
    @PostMapping
    public Result<ContactVO> add(@RequestBody @Validated ContactVO contactVO) {
        contactVO.setId(null);
        Contact contact = contactRepository.save(contactVO.convertTo());
        return Result.ok(new ContactVO().convertFrom(contact));
    }


    @ApiOperation("删除联系人")
    @DeleteMapping
    public Result<Void> del(@PathVariable("contactId") @ApiParam("联系人ID") String contactId) {
        contactRepository.deleteById(contactId);
        return Result.ok();
    }
}
