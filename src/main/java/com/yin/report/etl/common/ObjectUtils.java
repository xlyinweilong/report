package com.yin.report.etl.common;

import java.math.BigDecimal;

/**
 * 处理object转化
 *
 * @author yin.weilong
 * @date 2018.11.04
 */
public class ObjectUtils {

    public static BigDecimal getBigDecimal(Object o) {
        if (o == null) {
            return BigDecimal.ZERO;
        }
        if (o instanceof BigDecimal) {
            return (BigDecimal) o;
        } else if (o instanceof Double) {
            return BigDecimal.valueOf((Double) o);
        } else if (o instanceof String) {
            return BigDecimal.valueOf(Double.parseDouble(o.toString()));
        } else {
            return BigDecimal.valueOf(Double.parseDouble(o.toString()));
        }
    }

    public static Integer getInteger(Object o) {
        if (o == null) {
            return 0;
        } else if (o instanceof Integer) {
            return (Integer) o;
        } else if (o instanceof Double) {
            return (Integer) o;
        } else if (o instanceof String) {
            return Integer.parseInt(o.toString());
        } else {
            return Integer.parseInt(o.toString());
        }
    }


    public static String getString(Object o) {
        if (o == null) {
            return null;
        } else if (o instanceof String) {
            return o.toString().trim();
        } else {
            return o.toString().trim();
        }
    }
}
