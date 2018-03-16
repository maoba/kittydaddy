package com.kittydaddy.app.spider.heaven;

import java.util.LinkedList;

import java.util.LinkedList;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class UrlSpider {
    //实现UrlFilter接口
    private class Filter implements UrlFilter{
        public Boolean isNeed(String url) {
// TODO Auto-generated method stub
            if((url.startsWith("/") || url.indexOf("ygdy") > 0)&& !url.startsWith("ftp://")){
                return true;
            }else{
                return false;
            }
        }
        public String overUrl(String url) {
            if(url.startsWith("/")){
                url = "http://www.ygdy8.com"+url;
            }
            return url;
        }
    }
    public LinkedList<String> getUrls(String html){
//用于储存爬取的url的链表
        LinkedList<String> urls = new LinkedList<String>();
//解析html数据
        Document doc = Jsoup.parse(html);
//获取所有a标签
        Elements elements = doc.getElementsByTag("a");
        Filter urlfilter = null;
        try{
            urlfilter = new Filter();
        }catch(Exception e){

        }
        for(Element ele:elements){
//获取a标签中的所有链接
            String links = ele.attr("href");
            if(urlfilter.isNeed(links)){
                links = urlfilter.overUrl(links);
                urls.add(links);
            }
        }
        System.out.println("已经下载一个网页的所有url");
        return urls;
    }
}
