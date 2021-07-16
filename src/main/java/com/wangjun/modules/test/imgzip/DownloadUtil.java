package com.wangjun.modules.test.imgzip;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wangjun.modules.test.ZipTest;

import io.jsonwebtoken.lang.Collections;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wangjun
 */
@Slf4j
public class DownloadUtil {


    /**
     * 下载单个文件
     *
     * @param download
     * @param request
     * @param response
     * @throws IOException
     */
    public static void toDownloadFile(Download download, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String savePath = request.getServletContext().getRealPath(new StringBuffer(File.separator).append("attachment").append(File.separator).toString());
        log.info("The Path where file will storage is {}", savePath);

        downloadFile(getFile(download, savePath), request, response, true);
    }

    /**
     * 在服务器生成文件
     *
     * @param download
     * @param savePath
     * @return
     * @throws IOException
     */
    public static File getFile(Download download, String savePath) throws IOException {
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            URL url = new URL(download.getUrl());

            // 创建连接实例
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置超时间为3秒
            conn.setConnectTimeout(3000);
            //防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            conn.setRequestProperty("Charset", "UTF-8");


            //获取连接输入流
            inputStream = conn.getInputStream();
            //获取字节数组
            byte[] getData = readInputStream(inputStream);

            // 创建文件存放路径
            File saveDir = new File(savePath);
            if (!saveDir.exists()) {
                saveDir.mkdir();
            }


            // 在新建路径下创建对应附件名称的文件
            File file = new File(savePath, download.getName());
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(getData);

            return file;

        } catch (MalformedURLException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        } finally {
            // 关闭流
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    /**
     * 下载多个文件，并压缩成zip
     *
     * @param downloads 待下载的文件信息
     * @param request   Servlet请求
     * @param response  Servlet相应
     * @param zipName   待生成的zip文件名成
     * @throws IOException
     * @throws ServletException
     */
    public static void toDownloadFiles(List<List<Contents>> list, HttpServletRequest request, HttpServletResponse response, String zipName) throws Exception {
        if (!Collections.isEmpty(list)) {
            String path1 = request.getServletContext().getRealPath(new StringBuffer(File.separator).append("attachment").toString());
            // 存放文件路径
            List<String> sourcePath1 = Lists.newArrayList();
            // 创建文件存放路径
            File saveDir1 = new File(path1);
            if (!saveDir1.exists()) {
                saveDir1.mkdir();
            }

            for (List<Contents> contentsList : list){

                String path2 = path1 + (new StringBuffer(File.separator).append("test").toString());
                sourcePath1.add(path2);
                // 创建文件存放路径
                File saveDir2 = new File(path2);
                if (!saveDir2.exists()) {
                    saveDir2.mkdir();
                }
                for (Contents contents1 : contentsList) {
                    List<Download> downloads = contents1.getDownloads();
                    log.info("The total of Attachment is {}", downloads.size());
                    String savePath = path2 + new StringBuffer(File.separator).append(contents1.getName()).append(File.separator).toString();
                    log.info("The Path where file will storage is {}", savePath);

                    /**
                     * 处理同名文件，
                     * 如果存在，则以 a.txt, a(1).txt, a(2).txt
                     */
                    Map<String, Integer> map = Maps.newHashMap();
                    for (Download download : downloads) {
                        String fileName = download.getName();
                        if (map.containsKey(fileName)) {
                            map.put(fileName, map.get(fileName) + 1);
                        } else {
                            map.put(fileName, 0);
                        }
                        if (map.get(fileName) != 0) {
                            int position = fileName.lastIndexOf(".");
                            download.setName(new StringBuffer(fileName.substring(0, position)).append("(").append(map.get(fileName)).append(")").append(fileName.substring(position, fileName.length())).toString());
                        }
                        getFile(download, savePath);
                    }

                }
            }

            String zipFileName = path1 + zipName;

            // 生成zip文件
            ZipTest.toZip(sourcePath1, zipFileName, true);
            File fileZip = new File(zipFileName);
            // 下载zip文件
            downloadFile(fileZip, request, response, true);

            // 删除源文件
            //deleteSourceFile(contents);
        }
    }

    private static void deleteSourceFile(List<List<File>> files) {
        if (!Collections.isEmpty(files)) {
            files.forEach(list ->
                list.forEach(File::delete)
            );
        }
    }

    /**
     * 从输入流中获取字节数组，根据 available 获取一次性读取的字节数量
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        int count = 0;
        while (count == 0) {
            count = inputStream.available();
        }
        byte[] buffer = new byte[count];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        log.info("current file length is {}", len);
        bos.close();
        return bos.toByteArray();
    }

    /**
     * 生成zip文件
     *
     * @param files
     * @param savePath
     * @param zipName
     * @return
     */
    public static File generateZipFile(List<List<File>> files, String savePath, String zipName) throws IOException, ServletException {
        String zipFileName = zipName;
        if (Strings.isNullOrEmpty(zipFileName)) {
            zipFileName = UUID.randomUUID().toString();
        }
        zipFileName = zipFileName + ".zip";
        File fileZip = new File(savePath, zipFileName);
        FileOutputStream fileOutputStream = null; // 文件输出流
        ZipOutputStream zipOutputStream = null; // 压缩流
        try {
            fileOutputStream = new FileOutputStream(fileZip);
            zipOutputStream = new ZipOutputStream(fileOutputStream);
            zipFile(files, zipOutputStream);
        } catch (FileNotFoundException e) {
            throw e;
        } finally {
            if (zipOutputStream != null) {
                zipOutputStream.close();
            }
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        }

        return fileZip;
    }

    /**
     * 压缩文件
     *
     * @param contents
     * @param outputStream
     * @throws IOException
     * @throws ServletException
     */
    public static void zipFile(List<List<File>> files, ZipOutputStream outputStream) throws IOException, ServletException {
        try {

            int size = files.size();
            // 压缩列表中的文件
            for (int i = 0; i < size; i++) {
                File file = (File) files.get(i);
                zipFile(file, outputStream);
            }

        } catch (IOException e) {
            throw e;
        }
    }

    public static void zipFile(File inputFile, ZipOutputStream outputstream) throws IOException, ServletException {
        FileInputStream inStream = null;
        BufferedInputStream bInStream = null;
        try {
            if (inputFile.exists()) {
                if (inputFile.isFile()) {
                    inStream = new FileInputStream(inputFile);
                    bInStream = new BufferedInputStream(inStream);
                    ZipEntry entry = new ZipEntry(inputFile.getName());
                    outputstream.putNextEntry(entry);

                    // 最大的流
                    int MAX_BYTE = 20 * 1024 * 1024;
                    // 接受流的容量
                    long streamTotal = 0;
                    // 流需要分开的数量
                    int streamNum = 0;
                    // 文件剩下的字符数
                    int leaveByte = 0;
                    // byte数组接受文件的数据
                    byte[] inOutbyte;
                    // 通过available方法取得流的最大字符数
                    streamTotal = bInStream.available();
                    // 取得流文件需要分开的数量
                    streamNum = (int) Math.floor(streamTotal / MAX_BYTE);
                    // 分开文件之后,剩余的数量
                    leaveByte = (int) streamTotal % MAX_BYTE;

                    if (streamNum > 0) {
                        for (int j = 0; j < streamNum; ++j) {
                            inOutbyte = new byte[MAX_BYTE];
                            // 读入流,保存在byte数组
                            bInStream.read(inOutbyte, 0, MAX_BYTE);
                            // 写出流
                            outputstream.write(inOutbyte, 0, MAX_BYTE);
                        }
                    }
                    // 写出剩下的流数据
                    inOutbyte = new byte[leaveByte];
                    bInStream.read(inOutbyte, 0, leaveByte);
                    outputstream.write(inOutbyte);
                    outputstream.closeEntry();
                    // Closes the current ZIP entry
                    // and positions the stream for
                    // writing the next entry

                }
            } else {
                throw new ServletException("文件不存在！");
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (bInStream != null) {
                bInStream.close();
            }
            if (inStream != null) {
                inStream.close();
            }
        }
    }

    /**
     * 下载zip文件
     *
     * @param file
     * @param response
     * @param isDelete
     */
    public static void downloadFile(File file, HttpServletRequest request, HttpServletResponse response, boolean isDelete) throws IOException {
        BufferedInputStream bufferedInputStream = null;
        OutputStream outputStream = null;
        try {
            bufferedInputStream = new BufferedInputStream(new FileInputStream(file.getPath()));
            byte[] buffer = new byte[bufferedInputStream.available()];
            bufferedInputStream.read(buffer);


            response.reset();
            response.reset();
            outputStream = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            log.info("fileName:" + file.getName());

            String userAgent = request.getHeader("User-Agent");
            String formFileName = file.getName();

            // 针对IE或者以IE为内核的浏览器：
            if (userAgent.contains("MSIE") || userAgent.contains("Trident") || userAgent.contains("Edge")) {
                formFileName = java.net.URLEncoder.encode(formFileName, "UTF-8");
            } else {
                // 非IE浏览器的处理：
                formFileName = new String(formFileName.getBytes("UTF-8"), "ISO-8859-1");
            }
            response.setHeader("Content-Disposition", "attachment;filename=" + formFileName);
            outputStream.write(buffer);
            outputStream.flush();

            //是否将生成的服务器端文件删除
            if (isDelete) {
                file.delete();
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (bufferedInputStream != null) {
                bufferedInputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }
}
