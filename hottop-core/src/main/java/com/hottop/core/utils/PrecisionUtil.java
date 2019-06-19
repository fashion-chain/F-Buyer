package com.hottop.core.utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class PrecisionUtil {
    private static final RoundingMode defaultRoundMode = RoundingMode.DOWN;
    private static final MathContext mathContext = new MathContext(16, defaultRoundMode);

    private static BigDecimal bigDecimal(String value) {
        return new BigDecimal(value, mathContext).setScale(2, defaultRoundMode);
    }

    public static Long add(Long value1, Long value2) {
        return bigDecimal(value1.toString()).add(bigDecimal(value2.toString())).longValue();
    }

    public static Long minus(Long value1, Long value2) {
        return bigDecimal(value1.toString()).min(bigDecimal(value2.toString())).longValue();
    }

    public static String multiply(String value1, String value2) {
        return bigDecimal(value1).multiply(bigDecimal(value2), mathContext).toString();
    }

    public static Long multiply(Long value1, Long value2) {
        return bigDecimal(value1.toString()).multiply(bigDecimal(value2.toString()), mathContext).longValue();
    }

    public static Long divide(Long value1, Long value2) {
        return bigDecimal(value1.toString()).divide(bigDecimal(value2.toString()), mathContext).longValue();
    }

    public static String minusResultString(Long value1, Long value2) {
        return minus(value1, value2).toString();
    }

    public static String multiplyResultString(Long value1, Long value2) {
        return multiply(value1, value2).toString();
    }

    public static String divideResultString(Long value1, Long value2) {
        return divide(value1, value2).toString();
    }

    public static String addResultString(Long value1, Long value2) {
        return add(value1, value2).toString();
    }

    public static String getYuanByCent(Long amount) {
        return bigDecimal(amount.toString()).divide(bigDecimal("100"), mathContext).toString();
    }

    public static Long getCentByYuan(String amount) {
        return bigDecimal(amount).multiply(bigDecimal("100"), mathContext).longValue();
    }

    public static Long getCentByYuan(Double amount) {
        return bigDecimal(amount.toString()).multiply(bigDecimal("100"), mathContext).longValue();
    }
}
