package com.kittydaddy.app.spider.heaven;

import org.apache.http.client.methods.HttpGet;

/*
 * 设置Get方法的接口
 */
public interface GetMethodSet {
     HttpGet getGetMethod(String url);
}
