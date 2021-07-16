package com.wangjun.modules.test.aliyunpdf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.annotations.Case;
import org.assertj.core.util.Lists;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonSetter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wangjun
 */
@Slf4j
public class Pdf {
    public static void main(String[] args) {
        String host = "https://generalpdf.market.alicloudapi.com";
        String path = "/ocrservice/pdf";
        String method = "POST";
        String appcode = "8421e68b7c4b4993880d612c3cfe4226";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/json; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        /*String bodys = "{//文件数据：base64编码，要求base64编码后大小不超过100M，" +
                "页数不超过20页，和url参数只能同时存在一个\"fileBase64\":\"\"," +
                "//文件url地址：完整URL，URL长度不超过1024字节，URL对应的文件base64编码后大小不超过100M，" +
                "页数不超过20页，和img参数只能同时存在一个\"url\":\"\"," +
                "//是否需要识别结果中每一行的置信度，默认不需要。true：需要false：不需要\"prob\":false," +
                "//是否需要单字识别功能，默认不需要。true：需要false：不需要\"charInfo\":false," +
                "//是否需要自动旋转功能，默认不需要。true：需要false：不需要\"rotate\":false," +
                "//是否需要表格识别功能，默认不需要。true：需要false：不需要\"table\":false," +
                "//转文件类型，word\"fileType\":\"word\"}";
                */

        String bodys = "{\"fileBase64\":\"\"," +
                "\"url\":\"https://hosjoy-oss-test.oss-cn-hangzhou.aliyuncs.com/images/20201027/a79e670b-c478-4d9d-bf02-de5fec414de6.pdf\"," +
                "\"prob\":false," +
                "\"charInfo\":false," +
                "\"rotate\":false," +
                "\"table\":true," +
                "\"fileType\":\"word\"}";
        try {
            /*
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            String responseBody = EntityUtils.toString(response.getEntity());

            Obj obj = JSONObject.parseObject(responseBody, Obj.class);
            List<PageResults> pageResults = obj.getPageResults();
            Contract contract = new Contract();
            contract.setGuarantors(Lists.newArrayList());
            Guarantor guarantor = new Guarantor();
            List<Keyword> keywords = Lists.newArrayList();
            keywords.add(new Keyword(1, "债权人：(.*?)->"));
            keywords.add(new Keyword(2, "住所：(.*?)->"));
            keywords.add(new Keyword(3, "法定代表人：(.*?)->"));
            keywords.add(new Keyword(4, "第二条甲方所担保的主债权金额为人民币(.*?)元"));
            keywords.add(new Keyword(5, "姓名：(.*?)->"));
            keywords.add(new Keyword(6, "性别：(.*?)->"));
            keywords.add(new Keyword(7, "身份证件类型：(.*?)->"));
            keywords.add(new Keyword(8, "证件号码：(.*?)->"));
            keywords.add(new Keyword(9, "固定电话：(.*?)->"));
            keywords.add(new Keyword(10, "手机号码：(.*?)->"));
            keywords.add(new Keyword(11, "邮政编码：(.*?)->"));
            boolean b = false;
            for (PageResults r : pageResults) {
                for (Word w : r.getOcrResult().getPrismRowsInfo()) {
                    String s = w.getWord() + "->";
                    for (Keyword k : keywords) {
                        String str = getSubUtilSimple(s, k.word);

                        if (s.contains("保证人(甲方)详细信息")) {
                            b = true;
                            continue;
                        }
                        if (StringUtils.isEmpty(str)) {
                            continue;
                        }
                        log.info(str);
                        if (!b) {
                            switch (k.id) {
                                case 1:
                                    contract.setCreditor(str);
                                    break;
                                case 2:
                                    contract.setCreditorAddress(str);
                                    break;
                                case 3:
                                    contract.setLegalRepresentative(str);
                                    break;
                                case 4:
                                    contract.setAmount(str);
                                    break;
                                default:
                                    break;
                            }
                        } else {
                            switch (k.id) {
                                case 2:
                                    guarantor.setAddress(str);
                                    break;
                                case 5:
                                    if (null != guarantor.getName()) {
                                        contract.getGuarantors().add(guarantor);
                                        guarantor = new Guarantor();
                                    }
                                    guarantor.setName(str);
                                    break;
                                case 6:
                                    guarantor.setGender(str);
                                    break;
                                case 7:
                                    guarantor.setType(str);
                                    break;
                                case 8:
                                    guarantor.setCardNo(str);
                                    break;
                                case 9:
                                    guarantor.setFixedTelephone(str);
                                    break;
                                case 10:
                                    guarantor.setPhoneNumber(str);
                                    break;
                                case 11:
                                    guarantor.setPostalCode(str);
                                    break;
                                default:
                                    break;
                            }
                        }


                    }
                }
            }
            contract.getGuarantors().add(guarantor);
            log.info("关键字获取完毕");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Data
    private static class Keyword {
        private int id;
        private String word;

        public Keyword(int id, String word) {
            this.id = id;
            this.word = word;
        }
    }


    /**
     * 返回单个字符串，若匹配到多个的话就返回第一个，方法与getSubUtil一样
     *
     * @param soap
     * @param rgex
     * @return
     */
    public static String getSubUtilSimple(String soap, String rgex) {
        // 匹配的模式
        Pattern pattern = Pattern.compile(rgex);
        Matcher m = pattern.matcher(soap);
        while (m.find()) {
            return m.group(1);
        }
        return null;
    }


    @Data
    private static class Obj {
        private int totalPageNum;
        private List<PageResults> pageResults;
    }

    @Data
    private static class PageResults {
        private int index;
        private OcrResult ocrResult;
    }

    @Data
    private static class OcrResult {
        @JsonSetter("prism_rowsInfo")
        private List<Word> prismRowsInfo;
        private int orgWidth;
        private String content;
    }

    @Data
    private static class Word {
        private int rowId;
        private String word;
    }


    @Data
    public static class Contract {

        /**
         * 债权人
         */
        private String creditor;
        /**
         * 债权人地址
         */
        private String creditorAddress;
        /**
         * 法定代表人
         */
        private String legalRepresentative;
        /**
         * 债权金额
         */
        private String amount;

        private List<Guarantor> guarantors;
    }

    @Data
    public static class Guarantor {
        /**
         * 姓名
         */
        private String name;
        /**
         * 性别
         */
        private String gender;
        /**
         * 证件类型
         */
        private String type;
        /**
         * 证件号码
         */
        private String cardNo;
        /**
         * 固定电话
         */
        private String fixedTelephone;
        /**
         * 手机号码
         */
        private String phoneNumber;
        /**
         * 邮政编码
         */
        private String postalCode;
        /**
         * 住所
         */
        private String address;
    }


}
