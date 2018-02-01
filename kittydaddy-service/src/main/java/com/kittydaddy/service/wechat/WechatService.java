package com.kittydaddy.service.wechat;

import javax.servlet.http.HttpServletRequest;

public interface WechatService {
    
	/**
	 * 向微信服务器post数据
	 * @param request
	 * @return
	 */
	String weixinPost(HttpServletRequest request);

}
