package com.mashangshouche.car.util;

import java.util.Collection;

public class CollectionUtils {
    public static boolean isEmpty(Collection<?> collection) {
        if (null == collection || collection.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

}
