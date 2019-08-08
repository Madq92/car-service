package com.mashangshouche.car.util;

import java.util.UUID;

public class UUIDUtils {
    public static String id32() {
        String id = UUID.randomUUID().toString();
        return id.replace("-", "");
    }
}
