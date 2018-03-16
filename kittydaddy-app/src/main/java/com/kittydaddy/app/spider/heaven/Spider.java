package com.kittydaddy.app.spider.heaven;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Spider {
    public static void main(String[] args) {
        new Spider().URLSpider();
    }
    public void URLSpider(){
        //储存未被遍历的url数据
        Queue unVisit = new Queue();
        //已经遍历过的url
        HashSet<String> Visited = new HashSet<String>();
        //初始化Queue,加入初始url
        unVisit.push("http://www.ygdy8.com");
        //页面下载器
        HtmlDownloader htmldownloader = new HtmlDownloader();
        //链接下载器
        UrlSpider urlspider = new UrlSpider();
        //资源下载器
        ResourseDownloader resoursedownloader = new ResourseDownloader();
        //url种类判断器
        urlparse urlchooser = new urlparse();
        //资源保存器
        MysqlSave save = new MysqlSave();
        while(!unVisit.isempty()){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            // TODO Auto-generated catch block
                e.printStackTrace();
            }
            String url = unVisit.pop();
            if(!Visited.contains(url)){
                int num = urlchooser.geturlclass(url);

                //获取一个页面的html
                String html = null;
                try{
                    html = htmldownloader.getHtml(url);
                }catch(IllegalArgumentException e){
                    System.out.println(url+"非法的url");
                    continue;
                }
                    //如果是普通网页,下载网页的所有链接
                if(num == 0){
                    LinkedList<String> urls = new LinkedList<String>();
                    try{
                    //获得一个页面上的所有linkedList
                        urls = urlspider.getUrls(html);
                    }catch(Exception e){
                        System.out.println(url+"下载失败");
                        continue;
                    }
                    //将这个url加入到已遍历的队列中
                    Visited.add(url);
                    //将urls加入的队列中
                    Iterator<String> it = urls.iterator();
                    while(it.hasNext()){
                        String newurl = it.next();
                        if(!unVisit.contains(newurl)){
                            unVisit.push(newurl);
                        }
                    }
                    System.out.println("以遍历一个网站的所有url");
                }
                //如果是资源网站,下载并保存链接
                if(num == 1){
                    try{
                        System.out.println("开始下载:"+url);
                        HashMap<String,String> resourse = resoursedownloader.getResourse(html);
                        save.savetoSql(resourse);
                        System.out.println("下载了一个电影");
                        LinkedList<String> urls = new LinkedList<String>();
                        try{
                            //获得一个页面上的所有linkedList
                            urls = urlspider.getUrls(html);
                        }catch(Exception e){
                            System.out.println(url+"下载失败");
                            continue;
                        }

                        //将这个url加入到已遍历的队列中
                        Visited.add(url);
                        //将urls加入的队列中
                        Iterator<String> it = urls.iterator();
                        while(it.hasNext()){
                            String newurl = it.next();
                            if(!unVisit.contains(newurl)){
                                unVisit.push(newurl);
                            }
                        }
                        System.out.println("以遍历一个网站的所有url");
                    }catch(Exception e){
                        System.out.println("资源下载错误");
                        continue;
                    }
                }
            }
        }
        System.out.println("下载完毕");
    }

    private class urlparse implements UrlChoose{
        public int geturlclass(String url) {
            Pattern p = Pattern.compile(".+\\d+\\.html");
            Matcher m = p.matcher(url);
            if(m.matches()){
                return 1;
            }
            return 0;
        }
    }
}
