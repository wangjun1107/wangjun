package com.wangjun.modules.test;

import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * 标题
 *
 * @author wangjun
 */
public class MyHeaderFooter extends PdfPageEventHelper {
    /**
     * 总页数
     */
    private PdfTemplate totalPage;
    private Font hfFont;
    private Font watermarkFont;

    {
        try {
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            hfFont = new Font(bfChinese, 8, Font.NORMAL);
            watermarkFont = new Font(bfChinese, 40, Font.BOLD, new GrayColor(0.95f));
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开文档时，创建一个总页数的模版
     */
    @Override
    public void onOpenDocument(PdfWriter writer, Document document) {
        PdfContentByte cb = writer.getDirectContent();
        totalPage = cb.createTemplate(30, 12);
    }

    /**
     * 一页加载完成触发，写入页眉和页脚
     */
    @Override
    public void onEndPage(PdfWriter writer, Document document) {

        // 水印
        for(int i=0 ; i<4; i++) {
            for(int j=0; j<7; j++) {
                ColumnText.showTextAligned(writer.getDirectContentUnder(),
                        Element.ALIGN_CENTER,
                        new Phrase("好享家", watermarkFont),
                        (50.5f+i*150),
                        (40.0f+j*150),
                        writer.getPageNumber() % 2 == 1 ? 45 : -45);
            }
        }

        PdfPTable table = new PdfPTable(3);
        try {
            // 总宽
            table.setTotalWidth(PageSize.A4.getWidth()-80);
            // 设置表格宽3列
            table.setWidths(new int[]{24, 24, 3});
            table.setLockedWidth(true);
            table.getDefaultCell().setFixedHeight(0);
            table.getDefaultCell().setBorder(Rectangle.NO_BORDER);

            // 可以直接使用addCell(str)，不过不能指定字体，中文无法显示
            table.addCell(new Paragraph("hosjoy", hfFont));
            // 水平对齐
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(new Paragraph("第" + writer.getPageNumber() + "页/", hfFont));
            // 总页数
            PdfPCell cell = new PdfPCell(Image.getInstance(totalPage));
            // 去掉表格边框
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);
            // 将页眉写到document中，位置可以指定，指定到下面就是页脚
            table.writeSelectedRows(0, -1, 40, 20 , writer.getDirectContent());
        } catch (Exception de) {
            throw new ExceptionConverter(de);
        }
    }

    /**
     * 全部完成后，将总页数的pdf模版写到指定位置
     */
    @Override
    public void onCloseDocument(PdfWriter writer, Document document) {
        String text = "总" + (writer.getPageNumber()) + "页";
        ColumnText.showTextAligned(totalPage, Element.ALIGN_LEFT, new Paragraph(text, hfFont), 2, 2, 0);
    }

}
