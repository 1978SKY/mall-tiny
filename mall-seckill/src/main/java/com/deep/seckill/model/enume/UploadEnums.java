package com.deep.seckill.model.enume;

/**
 * 上架参数
 *
 * @author Deep
 * @date 2022/4/18
 */
public enum UploadEnums {
    /**
     * 最近一天
     */
    ONE_DAY(1),
    /**
     * 最近三天
     */
    THREE_DAY(3),
    /**
     * 最近一周
     */
    WEEK(7),
    /**
     * 最近一周
     */
    HALF_MONTH(15),
    /**
     * 最近一月
     */
    MONTH(30);

    private final int day;

    UploadEnums(int day) {
        this.day = day;
    }

    public int getDay() {
        return day;
    }
}
