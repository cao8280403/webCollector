package com.news.webcollector.controller;

import com.news.webcollector.entity.TbNews;
import com.news.webcollector.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class NewController {
    @Autowired
    private NewsService newsService;
    private static NewController newController;

    @PostConstruct //通过@PostConstruct实现初始化bean之前进行的操作
    public void init() {
        newController = this;
        newController.newsService = this.newsService;
        // 初使化时将已静态化的testService实例化
    }

    public void save(TbNews tbNews) {
        newController.newsService.save(tbNews);
    }

    public Boolean checkTitle(String tbNewsTitle) {
        Boolean flag = false;
        List<String> byTitle = newController.newsService.findByTitle(tbNewsTitle);
        //遍历查看是否存在其中
        if (byTitle.size()>0) {
            flag = true;
        }
        return flag;
    }
}
