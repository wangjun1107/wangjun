package com.wangjun.modules.test.pdf;


import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;
import org.assertj.core.util.Lists;
import org.json.JSONObject;

import com.alibaba.fastjson.JSONArray;
import com.baidu.aip.ocr.AipOcr;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wangjun
 */
@Data
@Slf4j
public class DemoApplication {
    //设置APPID/AK/SK
    public static final String APP_ID = "22678037";
    public static final String API_KEY = "Bg4im9tV4xCG1ma28bFsUS8I";
    public static final String SECRET_KEY = "bNUkE87Kk2tzjfCXoGg2hwpaxusU7qcx";
    public static final String DATE_FORMAT = "2020-09-17 11:50:21";

    /**
     * 解析pdf文档信息
     *
     * @param pdfPath pdf文档路径
     * @throws Exception
     */
    public static void pdfParse(String pdfPath) throws Exception {
        InputStream input = null;
        File pdfFile = new File(pdfPath);
        PDDocument document = null;
        try {
            input = new FileInputStream(pdfFile);
            //加载 pdf 文档
            document = PDDocument.load(input);

            /** 文档属性信息 **/
            PDDocumentInformation info = document.getDocumentInformation();
            System.out.println("标题:" + info.getTitle());
            System.out.println("主题:" + info.getSubject());
            System.out.println("作者:" + info.getAuthor());
            System.out.println("关键字:" + info.getKeywords());

            System.out.println("应用程序:" + info.getCreator());
            System.out.println("pdf 制作程序:" + info.getProducer());

            System.out.println("作者:" + info.getTrapped());

            System.out.println("创建时间:" + dateFormat(info.getCreationDate()));
            System.out.println("修改时间:" + dateFormat(info.getModificationDate()));


            //获取内容信息
            PDFTextStripper pts = new PDFTextStripper();
            String content = pts.getText(document);
            System.out.println("内容:" + content);


            /** 文档页面信息 **/
            PDDocumentCatalog cata = document.getDocumentCatalog();
            PDPageTree pages = cata.getPages();
            System.out.println(pages.getCount());
            int count = 1;

            // 初始化一个AipOcr
            AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

            // 可选：设置网络连接参数
            client.setConnectionTimeoutInMillis(2000);
            client.setSocketTimeoutInMillis(60000);

            List<List<Word>> listList = Lists.newArrayList();

            for (int i = 0; i < pages.getCount(); i++) {
                PDPage page = (PDPage) pages.get(i);
                if (null != page) {
                    PDResources res = page.getResources();
                    Iterable xobjects = res.getXObjectNames();
                    if (xobjects != null) {
                        Iterator imageIter = xobjects.iterator();
                        while (imageIter.hasNext()) {
                            COSName key = (COSName) imageIter.next();
                            if (res.isImageXObject(key)) {
                                try {
                                    PDImageXObject image = (PDImageXObject) res.getXObject(key);
                                    BufferedImage bimage = image.getImage();
                                    // 将BufferImage转换成字节数组
                                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                                    //png 为要保存的图片格式
                                    ImageIO.write(bimage, "png", out);
                                    byte[] barray = out.toByteArray();
                                    out.close();
                                    // 发送图片识别请求
                                    JSONObject json = client.basicGeneral(barray, new HashMap<>());
                                    List<Word> words = JSONArray.parseArray(json.get("words_result").toString(),Word.class);
                                    listList.add(words);
                                } catch (Exception e) {
                                    log.info(e.getMessage());
                                }
                            }
                        }
                    }
                }
            }

            listList.forEach(list ->{
//                list.forEach(word -> {
//                    System.out.println(word.getWords());
//                });
                list = list.stream()
                        .filter(word ->
                                word.getWords().contains("保证人:") ||
                                word.getWords().contains("债权人:") ||
                                word.getWords().contains("法定代表人:") ||
                                word.getWords().contains("所:") ||
                                word.getWords().contains("主债权金额") ||

                                word.getWords().contains("保证人(甲方)详细信息:") ||
                                word.getWords().contains("保证人(甲方):") ||
                                word.getWords().contains("姓名:") ||
                                word.getWords().contains("性别:") ||
                                word.getWords().contains("手机号码") ||
                                word.getWords().contains("证件号码:") ||
                                word.getWords().contains("身份证件类型:")

                        )
                        .collect(Collectors.toList());

                list.forEach(word -> {
                    log.info(word.getWords());
                });
            });

        } catch (Exception e) {
            throw e;
        } finally {
            if (null != input)
                input.close();
            if (null != document)
                document.close();
        }
    }

    @Data
    public static class Word{
        private String words;
    }

    /**
     * 获取格式化后的时间信息
     *
     * @param dar 时间信息
     * @return
     * @throws Exception
     */
    public static String dateFormat(Calendar calendar) throws Exception {
        if (null == calendar)
            return null;
        String date = null;
        try {
            String pattern = DATE_FORMAT;
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            date = format.format(calendar.getTime());
        } catch (Exception e) {
            throw e;
        }
        return date == null ? "" : date;
    }

    public static void main(String[] args) throws Exception {

        // 读取pdf文件
        String path = "C:/Users/wangjun/Desktop/好橙工保证合同/代松保证合同.pdf";
        pdfParse(path);

    }
}
