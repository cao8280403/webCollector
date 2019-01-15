package com.news.webcollector.crawler;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.rocks.BreadthCrawler;
import com.news.webcollector.controller.NewController;
import com.news.webcollector.entity.TbNews;
import com.news.webcollector.service.NewsService;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class WdzjNewsCrawler extends BreadthCrawler {
    private boolean flag = true;

    /**
     * @param crawlPath crawlPath is the path of the directory which maintains
     *                  information of this crawler
     * @param autoParse if autoParse is true,BreadthCrawler will auto extract
     *                  links which match regex rules from pag
     */
    public WdzjNewsCrawler(String crawlPath, boolean autoParse) {
        super(crawlPath, autoParse);
        /*start pages*/
        this.addSeed("https://www.wdzj.com");
        for (int pageIndex = 2; pageIndex <= 5; pageIndex++) {
            String seedUrl = String.format("https://www.wdzj.com/news/yc/3401268.html", pageIndex);
            this.addSeed(seedUrl);
        }

        /*fetch url like "https://blog.github.com/2018-07-13-graphql-for-octokit/" */
        this.addRegex("https://www.wdzj.com/news/yc/[0-9]{7}.html");
        /*do not fetch jpg|png|gif*/
        //this.addRegex("-.*\\.(jpg|png|gif).*");
        /*do not fetch url contains #*/
        //this.addRegex("-.*#.*");

        setThreads(10);
        getConf().setTopN(100);
//        getConf().setMaxExecuteCount(3);

        //enable resumable mode
        //setResumable(true);
    }

    @Override
    public void visit(Page page, CrawlDatums next) {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String sdf = simpleDateFormat.format(date);
        String url = page.url();
        /*if page is news page*/
        if (page.matchUrl("https://www.wdzj.com/news/yc/[0-9]{7}.html")) {

            /*extract title and content of news by css selector*/
            String title = page.select("h1[class=page-title]").first().text();
            String content = page.select("div[class=page-content]").toString();
            String page_time = page.selectText("div[class=page-time]");
            String replaceAll = content.replaceAll("\"//www", "\"http://www");
            if (page_time.contains(sdf)) {
                NewController newController = new NewController();
                //查看是否已经存在数据库，不存在则保存，参数是标题
                Boolean aBoolean = newController.checkTitle(title);
                if (!aBoolean) {
                    TbNews tbNews = new TbNews();
                    tbNews.setCreatetime(timestamp);
                    tbNews.setUpdatetime(timestamp);
                    tbNews.setNewsBody(replaceAll);
                    tbNews.setNewsClick((int) ((Math.random() * 9 + 1) * 1000));
                    tbNews.setNewsWebsite("网贷资讯");
                    tbNews.setNewsTitle(title);
                    tbNews.setGuid(UUID.randomUUID().toString());
                    tbNews.setNewsSourceDistributeTime(timestamp);
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
