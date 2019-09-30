package com.mashangshouche.car.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static String YYYYSMMSDD_HHMMSS = "yyyy-MM-dd HH:mm:ss";
    public static String YYYYSMMSDD = "yyyy-MM-dd";

    public static String format(Date date) {
        if (null == date) {
            return null;
        }
        DateFormat df = new SimpleDateFormat(YYYYSMMSDD);
        return df.format(date);
    }

    public static String format(Date date, String format) {
        if (null == date) {
            return null;
        }
        DateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    public static long diffToNow(long oldTime) {
        return System.currentTimeMillis() - oldTime;
    }
}
