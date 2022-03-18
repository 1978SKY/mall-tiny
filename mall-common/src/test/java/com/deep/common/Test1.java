package com.deep.common;

import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;

/**
 * @author Deep
 * @date 2022/3/17
 */
public class Test1 {
    public static void main(String[] args) {
        method(null);
    }

    private static void method(@NotNull String str) {
//        Assert.notNull(str,"str must not null!");
        System.out.println(str);
    }
}
