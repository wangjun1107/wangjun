package com.wangjun.modules.test.pdf;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * @author wangjun
 */
public class DateTimsTest {
    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        now = now.minus(30, ChronoUnit.DAYS);
        System.out.println(now.toString());
    }
}
