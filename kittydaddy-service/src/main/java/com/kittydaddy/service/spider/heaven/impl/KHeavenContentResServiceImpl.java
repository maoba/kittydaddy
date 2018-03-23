package com.kittydaddy.service.spider.heaven.impl;

import com.kittydaddy.common.enums.PublishStatusEnum;
import com.kittydaddy.common.enums.StatusEnum;
import com.kittydaddy.common.utils.KBeanUtil;
import com.kittydaddy.common.utils.KCollectionUtils;
import com.kittydaddy.metadata.spider.heaven.dao.KHeavenContentResEntityMapper;
import com.kittydaddy.metadata.spider.heaven.domain.KHeavenContentResEntity;
import com.kittydaddy.search.model.heavencontent.HeavenContentEntity;
import com.kittydaddy.search.service.heavencontent.HeavenContentService;
import com.kittydaddy.service.spider.heaven.KHeavenContentResService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * @author kittydaddy
 */
@Service
public class KHeavenContentResServiceImpl implements KHeavenContentResService{

    private static final Logger logger = LoggerFactory.getLogger(KHeavenContentResServiceImpl.class);

    @Autowired
    private KHeavenContentResEntityMapper heavenContentResEntityMapper;

    //搜索引擎service
    @Autowired
    private HeavenContentService heavenContentService;

    /**
     * 保存更新电影天堂的内容库
     * @param map
     */
    private String saveUpdateHeavenContent(Map<String,String> map){
        //获取下载资源链接
        String downLoadUrl = map.get("downloadUrl")==null?"":map.get("downloadUrl").toString();
        //获取标题信息
        String title = map.get("title") == null?"":map.get("title").toString();
        //获取主角的名称
        String roleName = map.get("roleName") == null?"":map.get("roleName").toString();
        //描述信息
        String summary = map.get("summary") == null?"":map.get("summary").toString();
        //获取评分信息
        String rate = map.get("rate")==null?"":map.get("rate").toString();
        //获取标题图片
        String titlePic = map.get("titlePic")==null?"":map.get("titlePic").toString();
        //获取内容的图片信息
        String summaryPic = map.get("summaryPic")==null?"":map.get("summaryPic").toString();
        //获取分类信息
        String genres = map.get("genres") ==null?"":map.get("genres").toString();

        List<KHeavenContentResEntity> resEntities = heavenContentResEntityMapper.findByTitle(title);
        if(KCollectionUtils.isEmpty(resEntities)){
            KHeavenContentResEntity resEntity = new KHeavenContentResEntity();
             resEntity.setCreateTime(new Date());
             resEntity.setDownloadUrl(downLoadUrl);
             resEntity.setGenres(genres);
             resEntity.setIsPublish(PublishStatusEnum.NO.getValue());
             resEntity.setRate(rate);
             resEntity.setStatus(StatusEnum.VALID.getValue());
             resEntity.setSummary(summary);
             resEntity.setTitlePic(titlePic);
             resEntity.setTitle(title);
             resEntity.setSummaryPic(summaryPic);
             resEntity.setRoleName(roleName);
             heavenContentResEntityMapper.insert(resEntity);
            return resEntity.getId();
        }else{
            logger.info("当前该资源已经存在");
        }
        return null;
    }

    private String downLoadHtml(String url) {
        String html = null;
        //创建一个请求客户端
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //声明一个相应
        HttpResponse response = null;
        //声明一个缓冲读取流
        BufferedReader reader = null;
        try {
            //获取响应
            response = httpclient.execute(getSimuHeader(url));
            //获取实体
            HttpEntity entity = response.getEntity();
            //获取流
            reader = new BufferedReader(new InputStreamReader(entity.getContent(),"GB2312"));
            //读取诗句
            String buff = null;
            StringBuilder sb = new StringBuilder();
            while ((buff = reader.readLine()) != null) {
                sb.append(buff);
            }
            html = sb.toString();
        } catch (IOException e) {
           logger.info(url + "的连接失败");
        }
        return html;
    }


    private HttpGet getSimuHeader(String url) {
        //创建一个get请求方法
        HttpGet getmethod = new HttpGet(url);
        //HttpHost proxy = new HttpHost("124.88.67.81",80);这里不设置代理IP
        //设置请求超时时间等
        RequestConfig responseconfig = RequestConfig.custom().setConnectionRequestTimeout(10000).setConnectTimeout(10000).setSocketTimeout(10000).build();
        //设置请求头,主要是user-agent
        getmethod.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
        //设置请求参数
        getmethod.setConfig(responseconfig);
        return getmethod;
    }

    /**
     * 爬取电影天堂的相关的电影的信息
     * @param map
     */
    @Override
    public void startCollectHeavenResource(Map<String, Object> map) {
        //储存未被遍历的url数据
        LinkedList<String> queue = new LinkedList<String>();
        //已经遍历过的url
        HashSet<String> visited = new HashSet<String>();
        //初始化Queue,加入初始url
        queue.add("http://www.ygdy8.com");

        while(!queue.isEmpty()){
            String url = queue.poll();
            if(!visited.contains(url)){
                boolean flag = geturlclass(url);
                //获取一个页面的html
                String html = null;
                try{
                    html = downLoadHtml(url);
                }catch(IllegalArgumentException e){
                    System.out.println(url+"非法的url");
                    continue;
                }

                //如果是资源网站,下载并保存链接
                if(flag){
                    try{
                        System.out.println("开始解析:"+url);
                        HashMap<String,String> resource = parseResource(html);
                        //1、进行入库处理
                        String id = saveUpdateHeavenContent(resource);

                        //2、自动发布处理
                        publishHeavenContent(id);

                        System.out.println("解析并入库电影"+resource.get("title"));
                        List<String> urls = new ArrayList<>();
                        try{
                            //获得一个页面上的所有linkedList
                            urls = getUrls(html);
                        }catch(Exception e){
                            System.out.println(url+"下载失败");
                            continue;
                        }

                        //将这个url加入到已遍历的队列中
                        visited.add(url);
                        //将urls加入的队列中
                        Iterator<String> it = urls.iterator();
                        while(it.hasNext()){
                            String newurl = it.next();
                            if(!queue.contains(newurl)){
                                queue.push(newurl);
                            }
                        }
                        System.out.println("以遍历一个网站的所有url");
                    }catch(Exception e){
                        System.out.println("资源下载错误");
                        continue;
                    }
                }else{
                    //普通网页,下载网页的所有链接
                    LinkedList<String> urls = new LinkedList<String>();
                    try{
                        //获得一个页面上的所有linkedList
                        urls = getUrls(html);
                    }catch(Exception e){
                        System.out.println(url+"下载失败");
                        continue;
                    }
                    //将这个url加入到已遍历的队列中
                    visited.add(url);
                    //将urls加入的队列中
                    Iterator<String> it = urls.iterator();
                    while(it.hasNext()){
                        String newurl = it.next();
                        if(!queue.contains(newurl)){
                            queue.add(newurl);
                        }
                    }
                    System.out.println("以遍历一个网站的所有url");
                }
            }
        }
        System.out.println("下载完毕");
    }



    //发布到搜索引擎
    private void publishHeavenContent(String id) {
        KHeavenContentResEntity entity = heavenContentResEntityMapper.selectByPrimaryKey(id);
        if(entity == null) return;
        if(PublishStatusEnum.NO.getValue() == entity.getIsPublish()){
            //如果没有被发布
            HeavenContentEntity contentEntity = (HeavenContentEntity) KBeanUtil.copy(entity,new HeavenContentEntity());
            heavenContentService.publish(contentEntity);//发布到搜索引擎

            entity.setUpdateTime(new Date());
            entity.setPublishTime(new Date());
            entity.setIsPublish(PublishStatusEnum.YES.getValue());
            heavenContentResEntityMapper.updateByPrimaryKey(entity);
        }
    }


    /**
     * 解析获取的网页信息
     * @param html
     * @return
     */
    public HashMap<String,String> parseResource(String html){
        HashMap<String,String> resourse = new HashMap<String,String>();
        Document doc = Jsoup.parse(html);
        //获取分类,介绍,豆瓣主演
        //获取分类
        Pattern f = Pattern.compile("类　　别\\W+\\<?+");
        Matcher fm = f.matcher(html);
        if(fm.find()){
            String genres = fm.group();
            if(genres != null && genres.length() > 6){
                genres = genres.substring(5,genres.length() - 1);
            }else{
                genres = "未知类型";
            }
            resourse.put("genres",genres);
        }
        //获取主演
        Pattern z = Pattern.compile("主　　演\\W+\\<?+");
        Matcher zm = z.matcher(html);
        if(zm.find()){
            String roleName = zm.group();
            if(roleName != null  && roleName.length() > 6){
                roleName = roleName.substring(5, roleName.length() - 1);
            }else{
                roleName = "未知主角";
            }
            resourse.put("roleName",roleName);
        }
        //获取介绍
        Pattern j = Pattern.compile("简　　介.+<img");
        Matcher jm = j.matcher(html);
        if(jm.find()){
            String summary = jm.group();
            if(summary != null && summary.length() > 10){
                summary = summary.replace("<br />","");
                summary = summary.substring(5,summary.length()-4);
            }else{
                summary = "无介绍";
            }
            resourse.put("summary", summary);
        }
        //获取豆瓣
        Pattern d = Pattern.compile("豆瓣评分.+/10");
        Matcher dm = d.matcher(html);
        if(dm.find()){
            String rate = dm.group();
            rate = rate.substring(5, rate.length()-3);
            resourse.put("rate", rate);
        }
        //获取标题
        Elements title = doc.getElementsByTag("title");
        resourse.put("title", title.text());
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
        resourse.put("titlePic", srcs[0]);
        resourse.put("summaryPic", srcs[1]);
        //获取资源
        Elements hrefs = doc.getElementsByTag("a");
        StringBuilder sb1 = new StringBuilder();
        for(Element href:hrefs){
            if(href.attr("href").startsWith("ftp://"))
                sb1.append(href.attr("href")+":~~~~:");
        }
        resourse.put("downloadUrl", sb1.toString());
        return resourse;
    }

    /**
     * 判断url是否为满足需求的url
     * @param url
     * @return
     */
    public Boolean isNeed(String url) {
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

    public LinkedList<String> getUrls(String html){
        //用于储存爬取的url的链表
        LinkedList<String> urls = new LinkedList<String>();
        //解析html数据
        Document doc = Jsoup.parse(html);
        //获取所有a标签
        Elements elements = doc.getElementsByTag("a");

        for(Element ele:elements){
            //获取a标签中的所有链接
            String links = ele.attr("href");
            if(isNeed(links)){
                links = overUrl(links);
                urls.add(links);
            }
        }
        System.out.println("已经下载一个网页的所有url");
        return urls;
    }

    public Boolean geturlclass(String url) {
        Pattern p = Pattern.compile(".+\\d+\\.html");
        Matcher m = p.matcher(url);
        if(m.matches())return true;
        return false;
    }

    public void cleanHeavenContentRes(Map<String, Object> map) {
        List<KHeavenContentResEntity> resEntities = heavenContentResEntityMapper.findByTitle(null);
        //获取没有下载地址的资源
        List<KHeavenContentResEntity> emptyEntities = resEntities.stream().filter((KHeavenContentResEntity heavenContent)
                ->heavenContent.getDownloadUrl().equals("")).collect(Collectors.toList());

        if(KCollectionUtils.isNotEmpty(emptyEntities)){
            for(KHeavenContentResEntity resEntity : emptyEntities){
                   //删除没有下载地址的资源
                   heavenContentResEntityMapper.deleteByPrimaryKey(resEntity.getId());

                   //搜索引擎删除没有下载地址的资源
                   heavenContentService.deleteById(resEntity.getId());
                   logger.info(resEntity.getTitle()+"—————>已删除");
            }
        }
    }
}
