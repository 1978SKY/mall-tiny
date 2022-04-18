package com.deep.coupon;

import com.deep.common.utils.DateUtil;

/**
 * @author Deep
 * @date 2022/4/18
 */
public class DateUtilsTest {
    public static void main(String[] args) {
        String startTime = DateUtil.getStartTime();
        String endTime = DateUtil.getEndTime(2);
        System.out.println(startTime + "\n" + endTime);
    }
}
