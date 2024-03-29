package com.wangjun.modules.jsoup;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author wangjun
 */
@Data
public class DateExcel {
    @ExcelProperty("会员等级")
    private String tag;
    @ExcelProperty("会员名称")
    private String companyName;

    @ExcelProperty("状态")
    private String state;
    @ExcelProperty("招标名称")
    private String nameOfTender;

    @ExcelProperty("招标单位")
    private String company;
    @ExcelProperty("项目")
    private String projectCode;
    @ExcelProperty("发布日期")
    private String pubtime;
    @ExcelProperty("截止日期")
    private String lastime;
    @ExcelProperty("详情")
    private String detail = "";
    @ExcelProperty("url")
    private String url;

}
