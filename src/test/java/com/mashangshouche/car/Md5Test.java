package com.mashangshouche.car;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.Md5Crypt;

public class Md5Test {
    public static void main(String[] args) {
        String s = Md5Crypt.apr1Crypt("123");
        System.out.println(s);
        String s1 = Md5Crypt.md5Crypt("123".getBytes());
        System.out.println(s1);
        String s3 = DigestUtils.md5Hex("1");
        System.out.println(s3);
    }
}
