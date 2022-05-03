package com.deep.common.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    /**
     * 校验时间
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return true/false
     */
    public static boolean checkTime(long start, long end) {
        long now = System.currentTimeMillis();
        return now >= start && now < end;
    }

    /**
     * 获取当前时间（格式：yyyy-MM-ss HH:mm:ss）
     *
     * @return 当前时间
     */
    public static String getStartTime() {
        LocalDate now = LocalDate.now();
        LocalTime min = LocalTime.MIN;
        LocalDateTime start = LocalDateTime.of(now, min);

        return start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 获取结束时间（格式：yyyy-MM-ss HH:mm:ss）
     *
     * @param day 时间间隔
     * @return 结束时间
     */
    public static String getEndTime(int day) {
        assert day > 0;
        LocalDate now = LocalDate.now();
        LocalDate plus = now.plusDays(day - 1);
        LocalTime max = LocalTime.MAX;
        LocalDateTime end = LocalDateTime.of(plus, max);

        return end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

}