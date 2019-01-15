package com.news.webcollector;

import cn.edu.hfut.dmic.contentextractor.ContentExtractor;
import cn.edu.hfut.dmic.contentextractor.News;
import com.news.webcollector.controller.NewController;
import com.news.webcollector.crawler.CaifuNewsCrawler;
import com.news.webcollector.crawler.WdzjNewsCrawler;
import com.news.webcollector.crawler.WyxwNewsCrawler;
import com.news.webcollector.entity.TbNews;
import com.news.webcollector.service.NewsService;
import com.news.webcollector.util.MyContentExtractor;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Configuration //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling // 2.开启定时任务
public class SimpleScheduleConfig {

    @Autowired
    private NewsService newsService;

    private Date date;
    private String sdf;
    private String sdf2;
//    private String sdf3;

    //3.添加定时任务
//    @Scheduled(cron = "*/1 * * * * ?")
    @Scheduled(cron = "0 */5 * * * ?")
//    @Scheduled(cron = "0 01 12 ? * * ")
    private void configureTasks() {
        date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        sdf3 = simpleDateFormat2.format(date);
//        date.setHours(date.getHours()+8);
        sdf = simpleDateFormat.format(date);
        sdf2 = simpleDateFormat2.format(date);
        System.err.println("执行定时任务: " + sdf2);
        //开始搜集新闻
        String wdzj = "https://www.wdzj.com/news/";
        String dfcf = "http://finance.eastmoney.com";
        String wyxw = "http://money.163.com";
        //解析url获得所有今日的新闻的href
        ArrayList<String> allHref = getAllHref1(wdzj);
        ArrayList<String> allHref2 = getAllHref2(dfcf);
        ArrayList<String> allHref3 = getAllHref3(wyxw);
        //解析链接集合获取对应页面的信息
        analyzeAllHref1(allHref);
        analyzeAllHref2(allHref2);
        analyzeAllHref3(allHref3);

    }

    //解析每个url的新闻主体，并存入数据库中
    private void analyzeAllHref1(ArrayList<String> allHref) {
        News news = null;
        String content = null;
        Element contentElement = null;
        for (String h3 : allHref) {
            String regex = "href=\"" + "(.*?)" + "\">";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(h3);
            while (matcher.find()) {
                String href = matcher.group(1);
                String url = href.replaceAll("//www", "https://www");
                //调用解析并存储到数据库
                try {
                    news = ContentExtractor.getNewsByUrl(url);
//                    content = ContentExtractor.getContentByUrl(url);
                    contentElement = ContentExtractor.getContentElementByUrl(url);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                int lengthOfcontentElement = contentElement.toString().length() * 3;
                int maxLength = 65536;
                //如果是当天新闻 并且长度小于最大值，则存入数据库
                if (null != news.getTime()) {
                    if (news.getTime().contains(sdf) && lengthOfcontentElement < maxLength) {
                        NewController newController = new NewController();
                        //查看是否已经存在数据库，不存在则保存，参数是标题
                        Boolean aBoolean = newController.checkTitle(news.getTitle());
                        if (!aBoolean) {
                            TbNews tbNews = new TbNews();
                            tbNews.setCreatetime(Timestamp.valueOf(sdf2));
                            tbNews.setUpdatetime(Timestamp.valueOf(sdf2));
                            tbNews.setNewsBody(contentElement.toString());
                            tbNews.setNewsClick((int) ((Math.random() * 9 + 1) * 1000));
                            tbNews.setNewsWebsite("网贷资讯");
                            tbNews.setNewsTitle(news.getTitle());
                            tbNews.setGuid(UUID.randomUUID().toString());
                            tbNews.setNewsSourceDistributeTime(Timestamp.valueOf(news.getTime()));
                            tbNews.setNewsSourceLink(url);
                            tbNews.setNewsSourcePlatform("网贷之家");
                            tbNews.setPlatForm("金融之窗");
                            tbNews.setNewsType("");
                            tbNews.setShowStatus(1);
                            newController.save(tbNews);
                        }
                    }
                }
            }
        }
    }

    //解析每个url的新闻主体，并存入数据库中
    private void analyzeAllHref2(ArrayList<String> allHref) {
        News news = null;
        String content = null;
        Element contentElement = null;
        for (String h3 : allHref) {
            h3 = "href=\"http://finance.eastmoney.com/a/" + h3;
            String regex = "href=\"" + "(.*?)" + "\" >";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(h3);
            while (matcher.find()) {
                String href = matcher.group(1);
                String url = href.replaceAll("//www", "https://www");
                //调用解析并存储到数据库
                try {
                    news = ContentExtractor.getNewsByUrl(url);
//                    content = ContentExtractor.getContentByUrl(url);
                    contentElement = ContentExtractor.getContentElementByUrl(url);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                int lengthOfcontentElement = contentElement.toString().length() * 3;
                int maxLength = 65536;
                //如果是当天新闻 并且长度小于最大值，则存入数据库
                if (null != news.getTime()) {
                    if (news.getTime().contains(sdf) && lengthOfcontentElement < maxLength) {
                        NewController newController = new NewController();
                        //查看是否已经存在数据库，不存在则保存，参数是标题
                        Boolean aBoolean = newController.checkTitle(news.getTitle());
                        if (!aBoolean) {
                            TbNews tbNews = new TbNews();
                            tbNews.setCreatetime(Timestamp.valueOf(sdf2));
                            tbNews.setUpdatetime(Timestamp.valueOf(sdf2));
                            tbNews.setNewsBody(contentElement.toString());
                            tbNews.setNewsClick((int) ((Math.random() * 9 + 1) * 1000));
                            tbNews.setNewsWebsite("东方财富");
                            tbNews.setNewsTitle(news.getTitle());
                            tbNews.setGuid(UUID.randomUUID().toString());
                            tbNews.setNewsSourceDistributeTime(Timestamp.valueOf(sdf2));
                            tbNews.setNewsSourceLink(url);
                            tbNews.setNewsSourcePlatform("东方财富");
                            tbNews.setPlatForm("金融之窗");
                            tbNews.setNewsType("");
                            tbNews.setShowStatus(1);
                            newController.save(tbNews);
                        }
                    }
                }
            }
        }
    }

    //解析每个url的新闻主体，并存入数据库中
    private void analyzeAllHref3(ArrayList<String> allHref) {
        News news = null;
        String content = null;
        Element contentElement = null;
        for (String h3 : allHref) {
            h3 = "<a href=\"http://money.163.com/19" + h3;
            String regex = "href=\"" + "(.*?)" + "\">";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(h3);
            while (matcher.find()) {
                String href = matcher.group(1);
                String url = href.replaceAll("//www", "https://www");
                //调用解析并存储到数据库
                try {
                    news = ContentExtractor.getNewsByUrl(url);
//                    content = ContentExtractor.getContentByUrl(url);
                    contentElement = ContentExtractor.getContentElementByUrl(url);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                int lengthOfcontentElement = contentElement.toString().length() * 3;
                int maxLength = 65536;
                //如果是当天新闻 并且长度小于最大值，则存入数据库
                if (null != news.getTime()) {
                    if (news.getTime().contains(sdf) && lengthOfcontentElement < maxLength) {
                        NewController newController = new NewController();
                        //查看是否已经存在数据库，不存在则保存，参数是标题
                        Boolean aBoolean = newController.checkTitle(news.getTitle());
                        if (!aBoolean) {
                            TbNews tbNews = new TbNews();
                            tbNews.setCreatetime(Timestamp.valueOf(sdf2));
                            tbNews.setUpdatetime(Timestamp.valueOf(sdf2));
                            tbNews.setNewsBody(contentElement.toString());
                            tbNews.setNewsClick((int) ((Math.random() * 9 + 1) * 1000));
                            tbNews.setNewsWebsite("网易财经");
                            tbNews.setNewsTitle(news.getTitle());
                            tbNews.setGuid(UUID.randomUUID().toString());
                            tbNews.setNewsSourceDistributeTime(Timestamp.valueOf(news.getTime()));
                            tbNews.setNewsSourceLink(url);
                            tbNews.setNewsSourcePlatform("网易新闻");
                            tbNews.setPlatForm("金融之窗");
                            tbNews.setNewsType("");
                            tbNews.setShowStatus(1);
                            newController.save(tbNews);
                        }
                    }
                }
            }
        }
    }

    //解析网贷之家
    private ArrayList<String> getAllHref1(String url) {
//        String content = null;
        Element contentElement = null;
        try {
//            News news = ContentExtractor.getNewsByUrl(url);
//            content = ContentExtractor.getContentByUrl(url);
            contentElement = ContentExtractor.getContentElementByUrl(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //先得到<h3>的内容
        String pram1 = "<h3>";
        String pram2 = "</h3>";
        ArrayList<String> h3s = getStrContainData(contentElement.toString(), pram1, pram2, false);
        return h3s;
    }

    //解析东方财富
    private ArrayList<String> getAllHref2(String url) {
        String content = null;
        String contentElement = null;
        try {
//            News news = ContentExtractor.getNewsByUrl(url);
            contentElement = MyContentExtractor.getContentElementByUrl(url);
//            content = ContentExtractor.getContentByUrl(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //先得到<h3>的内容
        String pram1 = "<a href=\"http://finance.eastmoney.com/a";
        String pram2 = "</a>";
        String regex = "importantNews";
        String substring = null;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(contentElement);
        while (matcher.find()) {
            int i = matcher.groupCount();
            int start = matcher.start();
            substring = contentElement.substring(start, start + 5000);
        }
        ArrayList<String> importantNewss = getStrContainData(substring, pram1, pram2, false);
        //合并
//        for (String string:lis) {
//            h3s.add(string);
//        }
        return importantNewss;
    }

    //解析网易新闻
    private ArrayList<String> getAllHref3(String url) {
//        String content = null;
        Element contentElement = null;
        try {
//            News news = ContentExtractor.getNewsByUrl(url);
//            content = ContentExtractor.getContentByUrl(url);
            contentElement = ContentExtractor.getContentElementByUrl(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //先得到<h3>的内容
        String pram1 = "<h2><a href=\"http://money.163.com/19";
        String pram2 = "</h2>";
        ArrayList<String> h2s = getStrContainData(contentElement.toString(), pram1, pram2, false);
        pram1 = "<h3><a href=\"http://money.163.com/19";
        pram2 = "</h3>";
        ArrayList<String> h3s = getStrContainData(contentElement.toString(), pram1, pram2, false);

        //合并
        for (String string : h3s) {
            h2s.add(string);
        }
        return h2s;
    }

    /**
     *      * 描述：获取字符串中被两个字符（串）包含的所有数据
     *      * @param str 处理字符串
     *      * @param start 起始字符（串）
     *      * @param end 结束字符（串）
     *      * @param isSpecial 起始和结束字符是否是特殊字符
     *      * @return Set<String>
     *     
     */
    public static ArrayList<String> getStrContainData(String str, String start, String end, boolean isSpecial) {
        ArrayList<String> result = new ArrayList<>();
//        if (isSpecial) {
//            start = "\\" + start;
//            end = "\\" + end;
//        }
        String regex = start + "(.*?)" + end;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            String key = matcher.group(1);
            if (!key.contains(start) && !key.contains(end)) {
                result.add(key);
            }
        }
        return result;
    }
}
