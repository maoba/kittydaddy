package com.kittydaddy.app.spider.heaven;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 通过url下载html页面的类
 */
public class HtmlDownloader {
    public String getHtml(String url) {
        String html = null;
        //创建一个请求客户端
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //声明一个相应
        HttpResponse response = null;
        //声明一个缓冲读取流
        BufferedReader reader = null;
        try {
            //获取响应
            response = httpclient.execute(new MethodSet().getGetMethod(url));
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
            // TODO Auto-generated catch block
            System.out.println(url + "的连接失败");
        }
        return html;
    }

    //实现获得Get方法的接口,在里面实现代理ip,请求头等设置
    private class MethodSet implements GetMethodSet {
        @Override
        public HttpGet getGetMethod(String url) {
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
    }

}
