package com.kittydaddy.common.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import com.kittydaddy.common.enums.EncryptionEnum;

/**
 * 密码相关生成工具类
 * @author kitty daddy
 *
 */
public class KCryptogramUtil {
      /**
       * 获取盐
       */
	  public static String getSalt(){
		  RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
		  String salt = randomNumberGenerator.nextBytes().toHex();
		  return salt;
	  }
	  
	  
	  /**
	     * 获取加密后的密码
	     * @param password 明文密码
	     * @param telephone 手机号码
	     * @param email 邮箱
	     * @return
	     */
	    public static String getEncryptPassword(String salt,String password,String telephone,String email){
	        if(StringUtils.isNotEmpty(telephone)){
	            return new SimpleHash(EncryptionEnum.ALGORITHMNAME.getValue(),password,
	                    ByteSource.Util.bytes(telephone + salt),Integer.parseInt(EncryptionEnum.HASHITERATIONS.getValue())).toHex();
	        }else if(StringUtils.isNotEmpty(email)){
	            return new SimpleHash(EncryptionEnum.ALGORITHMNAME.getValue(),password,
	                    ByteSource.Util.bytes(email + salt),Integer.parseInt(EncryptionEnum.HASHITERATIONS.getValue())).toHex();
	        }
	        return null;
	    }
}
