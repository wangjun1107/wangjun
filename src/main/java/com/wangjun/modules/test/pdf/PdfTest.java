package com.wangjun.modules.test.pdf;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.itextpdf.text.pdf.draw.LineSeparator;

import lombok.extern.slf4j.Slf4j;

/**
 * 标题
 *
 * @author wangjun
 */
@Slf4j
@RestController
@RequestMapping("/api/test")
public class PdfTest {

    /**
     * 定义全局的字体静态变量
     */
    private static Font titlefont;
    private static Font headfont;
    private static Font keyfont;
    private static Font textfont;
    private static Font font;

    /* 静态代码块*/
    static {
        try {
            // 不同字体（这里定义为同一种字体：包含不同字号、不同style）
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            titlefont = new Font(bfChinese, 16, Font.BOLD);
            headfont = new Font(bfChinese, 14, Font.BOLD);
            keyfont = new Font(bfChinese, 10, Font.BOLD);
            textfont = new Font(bfChinese, 10, Font.NORMAL);
            font = new Font(bfChinese, 14, Font.BOLD);

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
        Document document = new Document(PageSize.A4);

        // 2.建立一个书写器(Writer)与document对象关联
        PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
        writer.setEncryption(null, null, PdfWriter.ALLOW_PRINTING, PdfWriter.STANDARD_ENCRYPTION_128);
        // 水印 页眉/页脚
        writer.setPageEvent(new MyHeaderFooter());
        // 3.打开文档
        document.open();
        // 标题
        document.addTitle("hosjoy@pdf");
        // 作者
        document.addAuthor("wangjun");
        // 主题
        document.addSubject("iText pdf test");
        // 关键字
        document.addKeywords("test");
        // 创建者
        document.addCreator("Creator");
        // 4.向文档中添加内容
        generatePDF(document);
        // 5.关闭文档
        document.close();
    }

    /**
     * 生成PDF文件
     */
    private void generatePDF(Document document) throws Exception {

        // 段落
        Paragraph paragraph = new Paragraph("Hello I Text 5", titlefont);
        //设置文字居中 0靠左   1，居中     2，靠右
        paragraph.setAlignment(1);
        //设置左缩进
        paragraph.setIndentationLeft(12);
        //设置右缩进
        paragraph.setIndentationRight(12);
        //设置首行缩进
        paragraph.setFirstLineIndent(24);
        //行间距
        paragraph.setLeading(20f);
        //设置段落上空白
        paragraph.setSpacingBefore(5f);
        //设置段落下空白
        paragraph.setSpacingAfter(10f);

        // 直线
        Paragraph p1 = new Paragraph();
        p1.add(new Chunk(new LineSeparator()));

        // 点线
        Paragraph p2 = new Paragraph();
        p2.add(new Chunk(new DottedLineSeparator()));

        // 超链接
        Anchor anchor = new Anchor(new Paragraph("超链接", titlefont));
        anchor.setReference("www.baidu.com");

        // 添加图片
        Image image = Image.getInstance("http://devb2b.hosjoy.com/img/hosjoy_logo48@2x.595a425a.png");
        image.setAlignment(Image.ALIGN_LEFT);

        // 依照比例缩放
        image.scalePercent(20);

        // 表格
        PdfPTable table = createTable(new float[]{10, 12, 12, 10, 10});

        table.addCell(createCell("测试PDF表头标题", headfont, Element.ALIGN_CENTER, 6, false));

        table.addCell(createCell("编码", keyfont, Element.ALIGN_CENTER, BaseColor.GRAY));
        table.addCell(createCell("名称", keyfont, Element.ALIGN_CENTER, BaseColor.GRAY));
        table.addCell(createCell("价格", keyfont, Element.ALIGN_CENTER, BaseColor.GRAY));
        table.addCell(createCell("颜色", keyfont, Element.ALIGN_CENTER, BaseColor.GRAY));
        table.addCell(createCell("备注", keyfont, Element.ALIGN_CENTER, BaseColor.GRAY));
        int totalQuantity = 0;
        List<Product> list = new Product().getProductList();
        for (Product product : list) {
            table.addCell(createCell(product.getProductCode(), textfont));
            table.addCell(createCell(product.getProductName(), textfont));
            table.addCell(createCell(String.valueOf(product.getPrice()), textfont));
            table.addCell(createCell(product.getColor(), textfont));
            table.addCell(createCell(product.getRemark(), textfont));
            totalQuantity++;
        }
        table.addCell(createCell("总计", keyfont));
        table.addCell(createCell("", textfont, Element.ALIGN_CENTER, 3));
        table.addCell(createCell(totalQuantity + "", textfont, Element.ALIGN_CENTER));

        document.add(paragraph);
        document.add(anchor);
        document.add(p2);
        document.add(image);
        document.add(p1);
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
