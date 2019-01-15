package com.news.webcollector.crawler;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.rocks.BreadthCrawler;
import com.news.webcollector.controller.NewController;
import com.news.webcollector.entity.TbNews;
import com.news.webcollector.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class CaifuNewsCrawler extends BreadthCrawler {
    private boolean flag = true;

    /**
     * @param crawlPath crawlPath is the path of the directory which maintains
     *                  information of this crawler
     * @param autoParse if autoParse is true,BreadthCrawler will auto extract
     *                  links which match regex rules from pag
     */
    public CaifuNewsCrawler(String crawlPath, boolean autoParse) {
        super(crawlPath, autoParse);
        /*start pages*/
        this.addSeed("http://finance.eastmoney.com");
        for (int pageIndex = 2; pageIndex <= 5; pageIndex++) {
            String seedUrl = String.format("http://finance.eastmoney.com/a/20181116986301189.html", pageIndex);
            this.addSeed(seedUrl);
        }

        /*fetch url like "https://blog.github.com/2018-07-13-graphql-for-octokit/" */
        this.addRegex("http://finance.eastmoney.com/a/[0-9]{17}.html");
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
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        String sdf = simpleDateFormat.format(date);
        String url = page.url();
        /*if page is news page*/
        if (page.matchUrl("http://finance.eastmoney.com/a/[0-9]{17}.html")) {

            /*extract title and content of news by css selector*/
            String title = page.select("h1").first().text();
            String content = page.select("div[class=Body]").toString();
            String page_time = page.selectText("div[class=time]");
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
                    tbNews.setNewsWebsite("东方财富");
                    tbNews.setNewsTitle(title);
                    tbNews.setGuid(UUID.randomUUID().toString());
                    tbNews.setNewsSourceDistributeTime(timestamp);
                    tbNews.setNewsSourceLink(url);
                    tbNews.setNewsSourcePlatform("东方财富");
                    tbNews.setPlatForm("金融之窗");
                    tbNews.setNewsType("");
                    tbNews.setShowStatus(1);
                    newController.save(tbNews);
                }
            }
            /*If you want to add urls to crawl,add them to nextLink*/
            /*WebCollector automatically filters links that have been fetched before*/
            /*If autoParse is true and the link you add to nextLinks does not match the
              regex rules,the link will also been filtered.*/
            //next.add("http://xxxxxx.com");
        }
    }

}
