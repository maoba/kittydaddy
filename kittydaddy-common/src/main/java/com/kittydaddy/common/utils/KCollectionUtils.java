package com.kittydaddy.common.utils;

import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;

/**
 * 
 * @author kitty daddy
 * 集合工具类
 */
public class KCollectionUtils {
      //判断是否为空
	  public static boolean isEmpty(Collection<?> collection){
    	  return CollectionUtils.isEmpty(collection);
      }
	  
	  //判断集合类是否是非空
	  public static boolean isNotEmpty(Collection<?> collection){
		  return CollectionUtils.isNotEmpty(collection);
	  }
}
