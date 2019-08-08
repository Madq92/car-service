package com.mashangshouche.car.util;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PriceUtils {
    /**
     * 格式转换
     *
     * @param fen 以分为单位的价格
     * @return
     */
    public static String toYuan(Long fen) {
        if (null == fen) {
            return null;
        }
        if (0 == fen.intValue()) {
            return "0";
        }
        return new BigDecimal(fen).divide(new BigDecimal(100), 2, RoundingMode.UP).toString();
    }

    /**
     * 格式转换
     *
     * @param yuan 以元为单位的价格
     * @return
     */
    public static Long toFen(String yuan) {
        if (StringUtils.isEmpty(yuan)) {
            return null;
        }
        return new BigDecimal(yuan).multiply(new BigDecimal(100)).longValue();
    }
}
