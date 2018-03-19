package com.kittydaddy.common.utils;

import org.springframework.beans.BeanUtils;

/**
 * @author kittydaddy
 *  实体工具类
 */
public class KBeanUtil {
	/**
	 * 对象复制
	 * @param source 源对象
	 * @param target 目标对象
	 */
   public static Object copy(Object source,Object target){
	   BeanUtils.copyProperties(source, target);
	   return target;
   }
}
