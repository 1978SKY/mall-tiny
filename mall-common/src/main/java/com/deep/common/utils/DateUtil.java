package com.deep.common.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * date工具类
 *
 * @author Deep
 * @date 2022/3/25
 */
public class DateUtil {

    public static String getFormatDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    public static String getLocalFormatDate() {
        return LocalDate.now().format(DateTimeFormatter.ISO_DATE);
    }
}