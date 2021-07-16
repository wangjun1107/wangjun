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
    @ExcelProperty("项目编码")
    private String projectCode;
    @ExcelProperty("发布日期")
    private String pubtime;
    @ExcelProperty("时间开始")
    private String lastime;
    @ExcelProperty("时间结束")
    private String endime;

}
