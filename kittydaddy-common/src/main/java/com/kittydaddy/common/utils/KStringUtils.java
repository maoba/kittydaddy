package com.kittydaddy.common.utils;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 * @author kitty daddy
 * 自定义字符串相关工具类
 */
public class KStringUtils {
	/**
	 * 字符串类型的数据转换成Long类型的字符串
	 * @param ids
	 * @return
	 */
     public static List<String> splitString(String ids){
    	 List<String> idSets = new ArrayList<String>();
    	 if(StringUtils.isNotEmpty(ids)){
    		 String[] strs = ids.split(",");
    		 for(String str : strs){
    			 idSets.add(str);
    		 }
    	 }
    	 return idSets;
     }
     
     /**
      * 判断一个字符串是否为空
      * @param str
      */
     public static boolean isEmpty(String str){
    	 return StringUtils.isEmpty(str);
     }
     
     /**
      * 判断一个字符串不为空
      * @param str
      * @return
      */
     public static boolean isNotEmpty(String str){
    	 return StringUtils.isNotEmpty(str);
     }
}
