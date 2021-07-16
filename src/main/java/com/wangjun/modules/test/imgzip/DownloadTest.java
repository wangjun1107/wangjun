package com.wangjun.modules.test.imgzip;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.assertj.core.util.Lists;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;

/**
 * @author wangjun
 */
@RequestMapping("/download")
@RestController
public class DownloadTest {


    @GetMapping("/zip")
    public void zip(HttpServletRequest request, HttpServletResponse response, Download vo) throws Exception {
        vo.setName("123.png");
        vo.setUrl("https://hosjoy-hbp.oss-cn-hangzhou.aliyuncs.com/images/20200924/25cc81fc-f8ce-4081-a03b-0fd2ca0d7056.png");
        List<Download> downloads = Lists.newArrayList();
        downloads.add(vo);
        List<Download> downloads1 = Lists.newArrayList();
        //downloads1.add(new Download("111.png", "https://hosjoy-hbp.oss-cn-hangzhou.aliyuncs.com/images/20200924/25cc81fc-f8ce-4081-a03b-0fd2ca0d7056.png"));
        downloads1.add(new Download("112.png", "https://hosjoy-hbp.oss-cn-hangzhou.aliyuncs.com/images/20200924/ff5f3fd9-bf63-4f51-b763-c109a87ffd35.png"));
        List<Contents> contents = Lists.newArrayList();
        contents.add(new Contents("aaa", downloads));
        contents.add(new Contents("bbb", downloads1));

        List<List<Contents>> list = Lists.newArrayList();
        list.add(contents);
        DownloadUtil.toDownloadFiles(list, request, response, "Information.zip");
    }

    @GetMapping("/zip-oss")
    public void zipFilesDown(HttpServletRequest request, HttpServletResponse response){
//阿里云基础配置
        String endpoint = "cn-shanghai";
        String accessKeyId = "LTAI4GAWgGKUn3t4gBdoi47P";
        String accessKeySecret = "Uv1kGJstTMxpmURujgA6RNEHfanmFK";
        String bucketName = "test";
        String fileHost = "test";
        try {
            // 初始化
            OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);;
            String fileName =  "test.zip";
            // 创建临时文件
            File zipFile = File.createTempFile("test", ".zip");
            FileOutputStream f = new FileOutputStream(zipFile);

            CheckedOutputStream csum = new CheckedOutputStream(f, new Adler32());
            // 用于将数据压缩成Zip文件格式
            ZipOutputStream zos = new ZipOutputStream(csum);

            // 构造ListObjectsRequest请求。
            ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);
            // 列出userId目录下的所有文件和文件夹。
            listObjectsRequest.setPrefix(fileHost + "/test/");
            ObjectListing listing = ossClient.listObjects(listObjectsRequest);
            // 遍历所有文件。
            System.out.println("Objects:");
            for (OSSObjectSummary ossObjectSummary : listing.getObjectSummaries()) {
                System.out.println(ossObjectSummary.getKey());
                String eachFileName = ossObjectSummary.getKey().substring(ossObjectSummary.getKey().lastIndexOf("-")+1);
                // 获取Object，返回结果为OSSObject对象
                OSSObject ossObject = ossClient.getObject(bucketName, ossObjectSummary.getKey());
                // 读去Object内容  返回
                InputStream inputStream = ossObject.getObjectContent();
                // 对于每一个要被存放到压缩包的文件，都必须调用ZipOutputStream对象的putNextEntry()方法，确保压缩包里面文件不同名
                zos.putNextEntry(new ZipEntry(eachFileName));
                int bytesRead;
                // 向压缩文件中输出数据
                while((bytesRead=inputStream.read())!=-1){
                    zos.write(bytesRead);
                }
                inputStream.close();
                zos.closeEntry(); // 当前文件写完，定位为写入下一条项目
            }
            zos.close();
            String header = request.getHeader("User-Agent").toUpperCase();
            if (header.contains("MSIE") || header.contains("TRIDENT") || header.contains("EDGE")) {
                fileName = URLEncoder.encode(fileName, "utf-8");
                //IE下载文件名空格变+号问题
                fileName = fileName.replace("+", "%20");
            } else {
                fileName = new String(fileName.getBytes(), "ISO8859-1");
            }
            response.reset();
            response.setContentType("text/plain");
            response.setContentType("application/octet-stream; charset=utf-8");
            response.setHeader("Location", fileName);
            response.setHeader("Cache-Control", "max-age=0");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

            FileInputStream fis = new FileInputStream(zipFile);
            BufferedInputStream buff = new BufferedInputStream(fis);
            BufferedOutputStream out=new BufferedOutputStream(response.getOutputStream());
            byte[] car=new byte[1024];
            int l=0;
            while (l < zipFile.length()) {
                int j = buff.read(car, 0, 1024);
                l += j;
                out.write(car, 0, j);
            }
            // 关闭流
            fis.close();
            buff.close();
            out.close();

            ossClient.shutdown();
            // 删除临时文件
            zipFile.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    }
