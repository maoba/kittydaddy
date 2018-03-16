package com.kittydaddy.app.spider.heaven;

import java.util.LinkedList;

/**
 * 该类用于实现一个保存要访问的url队列
 * 先进先出,后进后出
 */
public class Queue {
    //用于保存要访问的url
    private LinkedList<String> queue = new LinkedList<String>();
    //添加元素的方法
    public void push(String url){
        queue.add(url);
    }
    //弹出一个元素的方法
    public String pop(){
        return queue.poll();
    }
    //判断是否为空的方法
    public Boolean isempty(){
        return queue.isEmpty();
    }
    //判断是否存在的方法
    public Boolean contains(String url){
        return queue.contains(url);
    }
}
