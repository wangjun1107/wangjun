package com.wangjun.modules.jsoup;

import java.util.List;
import java.util.Random;

import com.wangjun.modules.jsoup.mapper.CompanyMapper;
import org.assertj.core.util.Lists;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wangjun
 */
@Service
public class JsoupTest implements Runnable {
    @Autowired
    private CompanyMapper companyMapper;


    public List<DateExcel> getDateExcels() throws Exception {
        //List<Kw> kw = companyMapper.selectList(null);
        List<Kw> kw = Lists.newArrayList();
         kw.add(new Kw("", "会员标签"));

        List<DateExcel> dateExcels = Lists.newArrayList();

        for (Kw s : kw) {

            Random random = new Random();
            int ran = random.nextInt(10);
            Document document = Jsoup.connect("https://www.cjebuy.com/portal/list.do?chnlcode=result")
                    .ignoreContentType(true)
                    .userAgent(new DateList().ua[ran])
                    .data("kw", s.getName())
                    .post();


            Elements tbody = document.getElementsByClass("tbody");
            return null;
//            for (Element element : tbody) {
//                Elements li = element.getElementsByTag("li");
//                for (Element element1 : li) {
//                    DateExcel excel = new DateExcel();
//                    Elements pubtime = element1.getElementsByClass("pubtime fl f14 c9");
//                    Elements elementsByClass = element1.getElementsByClass("lh22");
//                    String s1 = null;
//                    for (Element e : pubtime) {
//                        s1 = e.getElementsByTag("script").toString().substring(26, 45);
//                    }
//                    String time = "";
//                    for (Element byClass : elementsByClass) {
//                        String lastime = byClass.getElementsByTag("script").toString().substring(24, 34);
//                        System.out.println(lastime);
//                        time = time + "   " + lastime;
//                    }
//                    // 招标名称
//                    String url = element1.getElementsByTag("a").attr("abs:href");
//                    String name = element1.getElementsByTag("a").text();
//                    // 招标单位
//                    String company = element1.getElementsByClass("company\tfl f14 c6").text();
//                    String project = element1.getElementsByClass("project fl f14 c6").text();
//                    String state = element1.getElementsByClass("state-txt").text();
//
//                    excel.setNameOfTender(name);
//                    excel.setProjectCode(project);
//                    excel.setPubtime(s1);
//                    excel.setUrl(url);
//                    excel.setCompany(company);
//                    excel.setCompanyName(s.getName());
//                    excel.setState(state);
//                    excel.setLastime(time);
//                    excel.setTag(s.getTag());
//                    dateExcels.add(excel);
//                }
//            }
//        }

//        for (DateExcel d : dateExcels) {
//            Random random = new Random();
//            int ran = random.nextInt(10);
//            Document document = Jsoup.connect(d.getUrl())
//                    .ignoreContentType(true)
//                    .userAgent(new DateList().ua[ran])
//                    .get();
//            Elements tbody = document.getElementsByTag("table");
//            for (Element element : tbody) {
//                Elements tr = element.getElementsByTag("tr");
//                for (Element element1 : tr) {
//                    Elements td1 = element1.getElementsByTag("td");
//                    for (int i = 0; i < td1.size(); i++) {
//                        String t = td1.get(i).text();
//                        if(t != null){
//                            d.setDetail(d.getDetail() + t +" ");
//                        }
//
//                    }
//                }
//            }
        }

        return dateExcels;
    }


    @Override
    public void run() {

    }


    public static class DateList {
        String[] ua = {"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:46.0) Gecko/20100101 Firefox/46.0",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.87 Safari/537.36 OPR/37.0.2178.32",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/534.57.2 (KHTML, like Gecko) Version/5.1.7 Safari/534.57.2",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2486.0 Safari/537.36 Edge/13.10586",
                "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko",
                "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0)",
                "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)",
                "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; WOW64; Trident/4.0)",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 BIDUBrowser/8.3 Safari/537.36",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.80 Safari/537.36 Core/1.47.277.400 QQBrowser/9.4.7658.400",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 UBrowser/5.6.12150.8 Safari/537.36",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.122 Safari/537.36 SE 2.X MetaSr 1.0",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36 TheWorld 7",
                "Mozilla/5.0 (Windows NT 6.1; W…) Gecko/20100101 Firefox/60.0"};
    }

}
