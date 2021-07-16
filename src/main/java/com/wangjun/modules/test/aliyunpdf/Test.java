package com.wangjun.modules.test.aliyunpdf;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wangjun
 */
@Slf4j
public class Test {
    public static void main(String[] args) {
        String rgex = "债权人：(.*?);";
        String str = getSubUtilSimple("债权人：好享家舒适智能家居股份有限公司(以下简称“乙方”);", rgex);
        log.info(str);
    }
    /**
     * 返回单个字符串，若匹配到多个的话就返回第一个，方法与getSubUtil一样
     * @param soap
     * @param rgex
     * @return
     */
    public static String getSubUtilSimple(String soap,String rgex){
        // 匹配的模式
        Pattern pattern = Pattern.compile(rgex);
        Matcher m = pattern.matcher(soap);
        while(m.find()){
            return m.group(1);
        }
        return "";
    }
}
