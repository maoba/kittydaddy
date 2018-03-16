package com.kittydaddy.app.spider.heaven;

public interface UrlFilter {
     Boolean isNeed(String url);
     String overUrl(String url);
}
