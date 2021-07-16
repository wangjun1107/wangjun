package com.wangjun.modules.test;

import java.util.Date;

import com.wangjun.common.utils.DateUtil;

/**
 * @author wangjun
 */
public class TestJava {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Date threeDayTime = DateUtil.getDateByDateOffset(new Date(), 0);

        System.out.println(DateUtil.formatDate(threeDayTime, DateUtil.DATE_FORMAT));
    }


}
