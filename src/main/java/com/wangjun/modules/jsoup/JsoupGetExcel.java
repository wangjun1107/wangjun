package com.wangjun.modules.jsoup;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.assertj.core.util.Lists;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;

/**
 * @author wangjun
 */
@Controller
@RequestMapping("/jsoup")
public class JsoupGetExcel {

    @GetMapping("/export")
    public void billAmountExport(HttpServletResponse response) throws Exception {
        List<DateExcel> dateExcels = Lists.newArrayList();
        //导出
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setHeader("Content-disposition", "attachment;filename=" +
                URLEncoder.encode("00XX00", String.valueOf(StandardCharsets.UTF_8)));
        EasyExcel.write(response.getOutputStream(), DateExcel.class)
                .sheet("00XX00")
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .doWrite(dateExcels);
    }
}
