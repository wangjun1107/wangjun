package com.wangjun.modules.test.pdf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.RectangleReadOnly;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;

/**
 * 标题
 *
 * @author wangjun
 */
@Slf4j
@RestController
@RequestMapping("/api/test-pdf")
public class CreatePdfUtil {

    /*
     * 定义全局的字体静态变量
     */

    /** font_20 **/
    private static Font font_20;
    private static Font font_8;
    private static Font font_8_red;

    /* 初始化字体大小 */
    static {
        try {
            // 不同字体（这里定义为同一种字体：包含不同字号、不同style）
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED, true);
            // Font.NORMAL 普通字体
            font_20 = new Font(bfChinese, 20, Font.NORMAL);
            font_8 = new Font(bfChinese, 8, Font.NORMAL);
            font_8_red = new Font(bfChinese, 8, Font.NORMAL);
            font_8_red.setColor(BaseColor.RED);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @RequestMapping("download-pdf-1")
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
        BaseColor baseColor = new BaseColor(216, 232, 245);
        // 表格
        PdfPTable table = createTable(new float[]{100, 80, 80, 80, 40, 80, 80, 90, 80, 80});
        table.setTotalWidth(800);
        table.setWidthPercentage(100);

        // 1
        table.addCell(createCellTitle());

        // 2
        table.addCell(createCell("项目名称", baseColor ));
        table.addCell(createCellAlignLeft("测试",9));

        // 3
        table.addCell(createCellColspan(" ",10));

        //4
        table.addCell(createCell("基本信息", baseColor));
        table.addCell(createCell("申请企业", baseColor));
        table.addCell(createCell("测试"));
        table.addCell(createCell("收款人名称", baseColor));
        table.addCell(createCellColspan("测试",2));
        table.addCell(createCell("合同总额", baseColor));
        table.addCell(createCell("125552.22"));
        table.addCell(createCell("期限", baseColor));
        table.addCell(createCellColspan("一个月",2));

        //5
        table.addCell(createCellRowspan("垫资打款信息",2, baseColor));
        table.addCell(createCell("垫资申请金额", baseColor));
        table.addCell(createCell("2522"));
        table.addCell(createCell("票面/打款总额", baseColor));
        table.addCell(createCellColspan("5532.55",2));
        table.addCell(createCell("打款类型", baseColor));
        table.addCell(createCell("125552.22"));
        table.addCell(createCell("保证金比例", baseColor));
        table.addCell(createCell("80%"));

        // 6
        table.addCell(createCell("联行号(银票)", baseColor));
        table.addCell(createCell("888"));
        table.addCell(createCell("收款人开户行(银票)", baseColor));
        table.addCell(createCellColspan("5532.55",2));
        table.addCell(createCell("收款人账号（银票）",  baseColor));
        table.addCell(createCellColspan("99999999", 3));

        // 7
        table.addCell(createCellRemark("特殊事项/要求说明：", 32,2,baseColor));
        table.addCell(createCellAlignLeft("好成功哈哈哈哈",8));

        // 8
        table.addCell(createCellRowspan("贷中岗", 9,baseColor));
        table.addCell(createCellRowspan("放款前落实条件", 9,baseColor));
        table.addCell(createCell("监管账户信息", baseColor));
        table.addCell(createCellColspan("888",7));

        table.addCell(createCell("应收账款质押完成", baseColor));
        table.addCell(createCellColspan("888", 7));

        table.addCell(createCell("评审决议流程", baseColor));
        table.addCell(createCellColspan("888",7));

        table.addCell(createCell("货款支付流程", baseColor));
        table.addCell(createCellColspan("888",7));

        table.addCell(createCell("预付款信息",baseColor));
        table.addCell(createCellColspan("888.55", 3));
        table.addCell(createCellColspan("2020-02-02", 2));
        table.addCell(createCellColspan("2020-02-02",2));

        table.addCell(createCell("已盖章上下游合同", baseColor));
        table.addCell(createCellColspanFontColor("已确认", 7,font_8_red));

        table.addCell(createCell("采购单编号", baseColor));
        table.addCell(createCellColspan("515541455",2));
        table.addCell(createCell("采购单状态", baseColor));
        table.addCell(createCellColspan("515541455", 2));
        table.addCell(createCellRowspan("采购明细是否确认？",2));
        table.addCell(createCellRowspanFontColor("已确认",2,font_8_red));

        table.addCell(createCell("支付单编号", baseColor));
        table.addCell(createCellColspan("515541455",2));
        table.addCell(createCell("支付单状态", baseColor));
        table.addCell(createCellColspan("515541455",2));

        table.addCell(createCellRemark("备注信息",32,0, baseColor));
        table.addCell(createCellColspan("cdcdcdcdcdscdsvsdfg",7));

        table.addCell(createCellColspan("确认交接请签字", 3,baseColor));
        table.addCell(createCellColspan("", 2));
        table.addCell(createCell("签名：", baseColor));
        table.addCell(createCellColspan(" ", 2));
        table.addCell(createCell("日期：", baseColor));
        table.addCell(createCellColspan("2021-06-11", 0));

        table.addCell(createCellColspan(" ", 10));

        table.addCell(createCellRowspan("资金部放款操作岗",2,baseColor));
        table.addCell(createCellRowspan("资料符合",2,baseColor));
        table.addCell(createCell("交接状态", baseColor));
        table.addCell(createCellColspanFontColor("已交接",7,font_8_red));

        table.addCell(createCellRemark("交接备注",32, 0,baseColor));
        table.addCell(createCellColspan("12312312",7));

        table.addCell(createCellColspan("确认交接请签字", 3, baseColor));
        table.addCell(createCellColspan("",2));
        table.addCell(createCell("签名：", baseColor));
        table.addCell(createCellColspan("", 2));
        table.addCell(createCell("日期：", baseColor));
        table.addCell(createCell("2021-06-11"));

        document.add(table);

    }


    /**
     * 创建单元格 头
     *
     */
    private PdfPCell createCellTitle() {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPhrase(new Phrase("好橙工放款交接单", font_20));
        cell.setFixedHeight(26);
        cell.setColspan(10);
        return cell;
    }

    /**
     * 创建单元格 头
     *
     */
    private PdfPCell createCellRemark(String text,int fixedHeight,int colspan,BaseColor baseColor) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setPhrase(new Phrase(text, font_8));
        cell.setFixedHeight(fixedHeight);
        cell.setBackgroundColor(baseColor);
        cell.setColspan(colspan);
        return cell;
    }
    /**
     * 创建单元格
     *
     * @param text 内容
     *             默认高度24
     *             字号8
     *             水平居中对齐
     */
    private PdfPCell createCell(String text) {
        PdfPCell cell = createCell();
        cell.setPhrase(new Phrase(text, font_8));
        return cell;
    }
    private PdfPCell createCell() {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setLeft(5f);
        cell.setTop(30f);
        cell.setFixedHeight(22);
        return cell;
    }
    private PdfPCell createCellAlignLeft(String text,int colspan) {
        PdfPCell cell = createCell();
        cell.setColspan(colspan);
        cell.setPhrase(new Phrase(text, font_8));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        return cell;
    }


    /**
     * 创建单元格（背景色）
     * @param value 内容
     * @param baseColor 颜色
     */
    private PdfPCell createCell(String value, BaseColor baseColor) {
        PdfPCell cell = createCell( value);
        cell.setBackgroundColor(baseColor);
        return cell;
    }

    /**
     * 创建单元格（背景色）
     * @param value 内容
     * @param font 字体颜色
     */
    private PdfPCell createCellFontColor(String value, Font font) {
        PdfPCell cell = createCell();
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }

    /**
     * 创建单元格（单元格跨x列合并）
     * @param value 内容
     * @param colspan N列合并
     */
    private PdfPCell createCellColspan(String value, int colspan) {
        PdfPCell cell = createCell( value);
        cell.setColspan(colspan);
        return cell;
    }
    /**
     * 创建单元格（单元格跨x列合并）
     * @param value 内容
     * @param colspan N列合并
     */
    private PdfPCell createCellColspanFontColor(String value, int colspan,Font font) {
        PdfPCell cell = createCell();
        cell.setColspan(colspan);
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }



    /**
     * 创建单元格（单元格跨x列合并）
     * @param value 内容
     * @param colspan N列合并
     * @param baseColor 背景颜色
     */
    private PdfPCell createCellColspan(String value, int colspan,BaseColor baseColor) {
        PdfPCell cell = createCell( value,baseColor);
        cell.setColspan(colspan);
        return cell;
    }

    /**
     * 创建单元格（单元格跨x行合并）
     * @param value 内容
     * @param rowspan N行
     */
    private PdfPCell createCellRowspan(String value,int rowspan) {
        PdfPCell cell = createCell( value);
        cell.setRowspan(rowspan);
        return cell;
    }
    /**
     * 创建单元格（单元格跨x行合并）
     * @param value 内容
     * @param rowspan N行
     */
    private PdfPCell createCellRowspanFontColor(String value,int rowspan,Font font) {
        PdfPCell cell = createCell();
        cell.setRowspan(rowspan);
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }

    /**
     *
     * 创建单元格（单元格跨x行合并）
     * @param value 内容
     *  @param rowspan N行
     * @param baseColor 背景颜色
     */
    private PdfPCell createCellRowspan(String value,int rowspan,BaseColor baseColor) {
        PdfPCell cell = createCell( value,baseColor);
        cell.setRowspan(rowspan);
        return cell;
    }


    /**
     * 创建单元格（指定字体、水平居..、单元格跨x列合并、设置单元格内边距）
     */
    private PdfPCell createCell(String value, boolean boderFlag) {
        PdfPCell cell = createCell( value);
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
