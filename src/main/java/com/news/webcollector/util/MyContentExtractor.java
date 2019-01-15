package com.news.webcollector.util;

import cn.edu.hfut.dmic.contentextractor.ContentExtractor;
import cn.edu.hfut.dmic.webcollector.plugin.net.OkHttpRequester;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class MyContentExtractor  {

    public static OkHttpRequester okHttpRequester = new OkHttpRequester();

    public static String getContentElementByUrl(String url) throws Exception {
        String html = okHttpRequester.getResponse(url).html();
        return (html);
    }

}
