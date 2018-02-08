package com.kittydaddy.common.utils;

public class KIntegerUtil {
	  /**
	   * 将传过来的带逗号的字符串转换成Integer类型
	   * @param str
	   */
      public static Integer str2Integer(String str){
    	   StringBuffer sb = new StringBuffer();
    	   String[] strs = str.split(",");
    	   if(strs !=null && strs.length>0){
    		   for(String str3 : strs){
    			   sb.append(str3);
    		   }
    	   }
    	  return Integer.parseInt(sb.toString());
      }
}
