package com.kittydaddy.app.spider.heaven;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 获得一个资源页面上的以下资源
 * 1分类
 * 2名字
 * 3主演
 * 4介绍
 * 5封面图片
 * 6介绍图片
 * 7地址
 * 8豆瓣评分
 */
public class ResourseDownloader {
    public HashMap<String,String> getResourse(String html){
        HashMap<String,String> resourse = new HashMap<String,String>();
        Document doc = Jsoup.parse(html);
        //获取分类,介绍,豆瓣主演
        //获取分类
        Pattern f = Pattern.compile("类　　别\\W+\\<?+");
        Matcher fm = f.matcher(html);
        if(fm.find()){
            String Classification = fm.group();
            if(Classification != null &&Classification.length() > 6){
                Classification = Classification.substring(5,Classification.length() - 1);
            }else{
                Classification = "未知类型";
            }
            resourse.put("Classification",Classification);
        }
        //获取主演
        Pattern z = Pattern.compile("主　　演\\W+\\<?+");
        Matcher zm = z.matcher(html);
        if(zm.find()){
            String role = zm.group();
            if(role != null  && role.length() > 6){
                role = role.substring(5, role.length() - 1);
            }else{
                role = "未知主角";
            }
            resourse.put("role",role);
        }
        //获取介绍
        Pattern j = Pattern.compile("简　　介.+<img");
        Matcher jm = j.matcher(html);
        if(jm.find()){
            String introduce = jm.group();
            if(introduce != null && introduce.length() > 10&&introduce.length()<800){
                introduce = introduce.replace("<br />","");
                introduce = introduce.substring(5,introduce.length()-4);
            }else{
                introduce = "无介绍";
            }
            resourse.put("introduce", introduce);
        }
        //获取豆瓣
        Pattern d = Pattern.compile("豆瓣评分.+/10");
        Matcher dm = d.matcher(html);
        if(dm.find()){
            String douban = dm.group();
            douban = douban.substring(5, douban.length()-3);
            resourse.put("douban", douban);
        }
        //获取标题
        Elements title = doc.getElementsByTag("title");
        resourse.put("movename", title.text());
        //获取图片
        Elements imgs = doc.getElementsByTag("img");
        String[] srcs = new String[2];
        int i = 0;
        for(Element img:imgs){
            if(img.attr("src").endsWith(".jpg")){
                if(i>=2){
                    break;
                }
                srcs[i] = img.attr("src");
                i++;
            }
        }
        resourse.put("titilepicture", srcs[0]);
        resourse.put("introducepicture", srcs[1]);
        //获取资源
        Elements hrefs = doc.getElementsByTag("a");
        StringBuilder sb1 = new StringBuilder();
        for(Element href:hrefs){
            if(href.attr("href").startsWith("ftp://"))
                sb1.append(href.attr("href")+":~~~~:");
        }
        resourse.put("address", sb1.toString());
        return resourse;
    }
}
