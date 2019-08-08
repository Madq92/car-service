package com.mashangshouche.car.util;


import org.springframework.beans.BeanUtils;

public class BeanCopyUtils {
    public static void copy(Object source, Object target){
        BeanUtils.copyProperties(source,target);
    }
}
