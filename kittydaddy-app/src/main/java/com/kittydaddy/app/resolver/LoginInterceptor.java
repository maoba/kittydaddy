package com.kittydaddy.app.resolver;

import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.kittydaddy.metadata.util.RedisUtil;
import com.kittydaddy.security.util.CookieUtils;
import com.kittydaddy.security.util.SessionRKGenerateUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author kitty daddy
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private static final Logger log = LoggerFactory.getLogger(LoginInterceptor.class);
    private static final String LOGIN_URL = "/login.html";
    @Autowired
    private RedisUtil redisUtil;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //表示不受拦截器拦截
    	if(request.getServletPath().endsWith("login.html") || 
        		request.getServletPath().endsWith("login.do") || 
        		request.getServletPath().endsWith("/wx/playVideo.html")) {
            return true;
        }
        
        BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext()); 
        redisUtil = (RedisUtil) factory.getBean("redisUtil"); 
        
        String sessionId = CookieUtils.getCookie(request, "sid");
        sessionId = SessionRKGenerateUtil.getByteKey(sessionId);
		Session session = (Session) redisUtil.getAttribute(sessionId);
        if(session != null) {
            return true;
        }
        response.sendRedirect(request.getContextPath() + LOGIN_URL);


        log.info(request.getContextPath() + LOGIN_URL);
        return false;
    }
}

