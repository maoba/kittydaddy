package com.kittydaddy.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author kitty daddy
 * 将 jsonp 对象进行解析
 */
public class KJSONPParser {
		/**
		 * 解析jsonp
		 * @param jsonp
		 * @return
		 */
	    public static JSONObject parseJSONP(String jsonp){
	    	String json;
	    	try{
	    		int startIndex = jsonp.indexOf("(");
				int endIndex = jsonp.lastIndexOf(")");
				json = jsonp.substring(startIndex+1, endIndex);
	    	}catch(Exception e){
	    		json = "";
	    	}
			return JSON.parseObject(json);
		}
	    
	    public static void main(String[] args) {
	    	String url = "http://vibll.tv.uc.cn/mobile/page/channel/1.0.0?start=0&size=30&channel=%E7%94%B5%E8%A7%86%E5%89%A7&genres=&platform=1&year=&area=&order=1&callback=jsonp4";
			String content = KHttpClientUtil.doGet(url);
			JSONObject json = parseJSONP(content);
			System.out.println(json);
		}
	
}
