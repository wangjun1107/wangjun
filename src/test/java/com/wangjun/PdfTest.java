package com.wangjun;

import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * 标题
 *
 * @author wangjun
 */
public class PdfTest {

    public static void main(String[] args) throws DocumentException, IOException {

        // 创建一个Document对象（pdf文档） A4纸张大小
        Document document = new Document(PageSize.A4);
// 建立一个书写器(Writer)与document对象关联
        PdfWriter.getInstance(document, new FileOutputStream("D:\\Test.pdf"));
// 打开文档
        document.open();

// 向文档中输入一个内容
        document.add(new Paragraph("Hello World"));

//关闭文档
        document.close();

    }
}
