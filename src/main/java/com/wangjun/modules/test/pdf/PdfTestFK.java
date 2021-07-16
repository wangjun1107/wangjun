package com.wangjun.modules.test.pdf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.RectangleReadOnly;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import lombok.extern.slf4j.Slf4j;

/**
 * 标题
 *
 * @author wangjun
 */
@Slf4j
@RestController
@RequestMapping("/api/test-pdf")
public class PdfTestFK {

    /**
     * 定义全局的字体静态变量
     */
    private static Font titlefont;
    private static Font headfont;
    private static Font keyfont;
    private static Font textfont;
    private static Font font;
    private static Font h;

    /* 静态代码块*/
    static {
        try {
            // 不同字体（这里定义为同一种字体：包含不同字号、不同style）
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED, true);
            titlefont = new Font(bfChinese, 20, Font.NORMAL);
            headfont = new Font(bfChinese, 14, Font.NORMAL);
            keyfont = new Font(bfChinese, 10,  Font.NORMAL);
            textfont = new Font(bfChinese, 10, Font.NORMAL);
            font = new Font(bfChinese, 14, Font.NORMAL);
            h = new Font(bfChinese, 10, Font.NORMAL);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @RequestMapping("download-pdf")
    public void downloadFileAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String fileName = "hello.pdf";
        // attachment; 下载附件方式
        response.setCharacterEncoding(request.getCharacterEncoding());
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "filename=" + fileName);
        // 1.新建document对象
        Document document = new Document(new RectangleReadOnly(842,595));

        // 2.建立一个书写器(Writer)与document对象关联
        PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
        writer.setEncryption( null, null, PdfWriter.ALLOW_PRINTING, PdfWriter.STANDARD_ENCRYPTION_128);
        // 水印 页眉/页脚
        // writer.setPageEvent(new MyHeaderFooter());
        // 3.打开文档
        document.open();
        // 标题
        document.addTitle("hosjoy@pdf");
        // 作者
        document.addAuthor("wangjun");
        // 主题
        document.addSubject("放款交接单下载");
        // 关键字
        document.addKeywords("放款交接单");
        // 创建者
        document.addCreator("wangjun");
        // 4.向文档中添加内容
        generatePDF(document);
        // 5.关闭文档
        document.close();
    }

    /**
     * 生成PDF文件
     */
    private void generatePDF(Document document) throws Exception {

        // 表格
        PdfPTable table = createTable(new float[]{80, 80, 80, 80, 80, 80, 80, 80, 80, 80});
        table.setTotalWidth(800);
        table.setWidthPercentage(100);

        // 1
        table.addCell(createCell("好橙工放款交接单", titlefont, Element.ALIGN_CENTER, 10));

        // 2
        table.addCell(createCell("项目名称", keyfont, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        table.addCell(createCell("测试", keyfont, Element.ALIGN_CENTER, 9));

        // 3
        table.addCell(createCell(" ", h, Element.ALIGN_CENTER, 10,true));

        //4
        table.addCell(createCell("基本信息", keyfont, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        table.addCell(createCell("申请企业", keyfont, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        table.addCell(createCell("测试", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("收款人名称", keyfont, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        table.addCell(createCell("测试", keyfont, Element.ALIGN_CENTER,2));
        table.addCell(createCell("合同总额", keyfont, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        table.addCell(createCell("125552.22", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("期限", keyfont, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        table.addCell(createCell("一个月", keyfont, Element.ALIGN_CENTER,2));

        //5
        table.addCell(createCell("垫资打款信息", keyfont, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        table.addCell(createCell("垫资申请金额", keyfont, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        table.addCell(createCell("2522", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("票面/打款总额", keyfont, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        table.addCell(createCell("5532.55", keyfont, Element.ALIGN_CENTER,2));
        table.addCell(createCell("打款类型", keyfont, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        table.addCell(createCell("125552.22", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("保证金比例", keyfont, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        table.addCell(createCell("80%", keyfont, Element.ALIGN_CENTER));

        // 6
        table.addCell(createCell("垫资打款信息", keyfont, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        table.addCell(createCell("联行号(银票)", keyfont, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        table.addCell(createCell("888", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("收款人开户行(银票)", keyfont, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        table.addCell(createCell("5532.55", keyfont, Element.ALIGN_CENTER,2));
        table.addCell(createCell("收款人账号（银票）", keyfont, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        table.addCell(createCell("99999999", keyfont, Element.ALIGN_CENTER,3));

        // 7
        table.addCell(createCell("特殊事项/要求说明：", keyfont, Element.ALIGN_CENTER,2,BaseColor.LIGHT_GRAY));
        table.addCell(createCell("sfklsadbfkhjb", keyfont, Element.ALIGN_CENTER,8));

        // 8
        table.addCell(createCell("贷中岗", keyfont, Element.ALIGN_CENTER,0,9));
        table.addCell(createCell("放款前落实条件", keyfont, Element.ALIGN_CENTER,0,9));
        table.addCell(createCell("监管账户信息", keyfont, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        table.addCell(createCell("888", keyfont, Element.ALIGN_CENTER,7));

        table.addCell(createCell("应收账款质押完成", keyfont, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        table.addCell(createCell("888", keyfont, Element.ALIGN_CENTER,7));

        table.addCell(createCell("评审决议流程", keyfont, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        table.addCell(createCell("888", keyfont, Element.ALIGN_CENTER,7));

        table.addCell(createCell("货款支付流程", keyfont, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        table.addCell(createCell("888", keyfont, Element.ALIGN_CENTER,7));

        table.addCell(createCell("预付款信息", keyfont, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        table.addCell(createCell("888.55", keyfont, Element.ALIGN_CENTER,3));
        table.addCell(createCell("2020-02-02", keyfont, Element.ALIGN_CENTER,2));
        table.addCell(createCell("2020-02-02", keyfont, Element.ALIGN_CENTER,2));

        table.addCell(createCell("已盖章上下游合同", keyfont, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        table.addCell(createCell("已确认", keyfont, Element.ALIGN_CENTER,7));

        table.addCell(createCell("采购单编号", keyfont, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        table.addCell(createCell("515541455", keyfont, Element.ALIGN_CENTER,2));
        table.addCell(createCell("采购单状态", keyfont, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        table.addCell(createCell("515541455", keyfont, Element.ALIGN_CENTER,2));
        table.addCell(createCell("采购明细是否确认？", keyfont, Element.ALIGN_CENTER,0,2));
        table.addCell(createCell("确认", keyfont, Element.ALIGN_CENTER));

        table.addCell(createCell("支付单编号", keyfont, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        table.addCell(createCell("515541455", keyfont, Element.ALIGN_CENTER,2));
        table.addCell(createCell("支付单状态", keyfont, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        table.addCell(createCell("515541455", keyfont, Element.ALIGN_CENTER,2));
        table.addCell(createCell("确认", keyfont, Element.ALIGN_CENTER));

        table.addCell(createCell("备注信息", keyfont, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        table.addCell(createCell("cdcdcdcdcdscdsvsdfg", keyfont, Element.ALIGN_CENTER,7));

        table.addCell(createCell("确认交接请签字", keyfont, Element.ALIGN_CENTER, 3));
        table.addCell(createCell("", keyfont, Element.ALIGN_CENTER, 2));
        table.addCell(createCell("签名：", keyfont, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        table.addCell(createCell("", keyfont, Element.ALIGN_CENTER, 2));
        table.addCell(createCell("日期：", keyfont, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        table.addCell(createCell("2021-06-11", keyfont, Element.ALIGN_CENTER, 0));

        table.addCell(createCell(" ", h, Element.ALIGN_CENTER, 10, false));

        table.addCell(createCell("资金部放款操作岗", keyfont, Element.ALIGN_CENTER,0,2));
        table.addCell(createCell("资料符合", keyfont, Element.ALIGN_CENTER,0,2));
        table.addCell(createCell("交接状态", keyfont, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        table.addCell(createCell("已交接", keyfont, Element.ALIGN_CENTER,7));

        table.addCell(createCell("交接备注", keyfont, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        table.addCell(createCell("12312312", keyfont, Element.ALIGN_CENTER,7));

        table.addCell(createCell("确认交接请签字", keyfont, Element.ALIGN_CENTER, 3));
        table.addCell(createCell("", keyfont, Element.ALIGN_CENTER, 2));
        table.addCell(createCell("签名：", keyfont, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        table.addCell(createCell("", keyfont, Element.ALIGN_CENTER, 2));
        table.addCell(createCell("日期：", keyfont, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        table.addCell(createCell("2021-06-11", keyfont, Element.ALIGN_CENTER, 0));

        document.add(table);

    }


    /**
     * 创建单元格
     */
    private PdfPCell createCell(String value) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPhrase(new Phrase(value, font));
        cell.setLeft(10f);
        cell.setTop(50f);
        cell.setFixedHeight(22);
        return cell;
    }

    /**
     * 创建单元格(指定字体)
     */
    private PdfPCell createCell(String value, Font font) {
        PdfPCell cell = createCell(value);
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }



    /**
     * 创建单元格（指定字体、水平）
     */
    private PdfPCell createCell(String value, Font font, int align) {
        PdfPCell cell = createCell( value,  font);
        cell.setHorizontalAlignment(align);
        return cell;
    }

    /**
     * 创建单元格（指定字体、水平）
     */
    private PdfPCell createCell(String value, Font font, int align, BaseColor baseColor) {
        PdfPCell cell = createCell( value,  font,align);
        cell.setBackgroundColor(baseColor);
        return cell;
    }

    /**
     * 创建单元格（指定字体、水平居..、单元格跨x列合并）
     */
    private PdfPCell createCell(String value, Font font, int align, int colspan) {
        PdfPCell cell = createCell( value,  font,align);
        cell.setColspan(colspan);
        return cell;
    }


    /**
     * 创建单元格（指定字体、水平居..、单元格跨x列合并）
     */
    private PdfPCell createCell(String value, Font font, int align, int colspan,BaseColor baseColor) {
        PdfPCell cell = createCell( value,  font,align,baseColor);
        cell.setColspan(colspan);
        return cell;
    }

    private PdfPCell createCell(String value, Font font, int align, int colspan,int rowspan) {
        PdfPCell cell = createCell( value,  font,align);
        cell.setRowspan(rowspan);
        return cell;
    }
    /**
     * 创建单元格（指定字体、水平居..、单元格跨行合并）
     */
    private PdfPCell createCell(String value, Font font, int align, int colspan,int rowspan,BaseColor baseColor) {
        PdfPCell cell = createCell( value,  font,align,baseColor);
        cell.setRowspan(rowspan);
        return cell;
    }

    /**
     * 创建单元格（指定字体、水平居..、单元格跨x列合并、设置单元格内边距）
     */
    private PdfPCell createCell(String value, Font font, int align, int colspan, boolean boderFlag) {
        PdfPCell cell = createCell( value,  font,align,colspan);
        cell.setPadding(3.0f);
        if (!boderFlag) {
            cell.setBorder(0);
            cell.setPaddingTop(15.0f);
            cell.setPaddingBottom(8.0f);
        } else {
            cell.setBorder(0);
            cell.setPaddingTop(0.0f);
            cell.setPaddingBottom(15.0f);
        }
        return cell;
    }

    /**
     * 创建指定列宽、列数的表格
     */
    private PdfPTable createTable(float[] widths) {
        PdfPTable table = new PdfPTable(widths);
        table.setTotalWidth(520);
        table.setLockedWidth(true);
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setBorder(1);
        return table;
    }
}
