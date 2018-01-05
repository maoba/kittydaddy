package com.kittydaddy.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

/**
 * @author kitty daddy
 *
 */
public class KDateUtils {
	 
	 /**
	  * 将时间的字符串转换成指定的时间
	  * @param dateStr
	  * @param patterns
	  * @return
	  */
     public static Date parseDate(String dateStr,String... patterns){
    	 Date formatDate = null;
    	 try {
    		 formatDate = DateUtils.parseDate(dateStr, patterns);
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	 return formatDate;
     }
     
     /**
      * 将日期类型转换成指定的字符串类型
      * @param date
      * @param pattern
      * @return
      */
     public static String format(Date date,String pattern){
    	 SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    	 return sdf.format(date);
     }
}
