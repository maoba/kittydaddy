package com.kittydaddy.security.service;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kittydaddy.common.exception.ParamCheckException;
import com.kittydaddy.facade.dto.system.request.UserLoginRequest;
import com.kittydaddy.facade.dto.system.response.UserResponse;
import com.kittydaddy.security.config.SystemToken;
import com.kittydaddy.service.system.UserService;
/**
 * @author kitty daddy
 */
@Service
public class SecurityService {
	@Autowired
	private UserService userService;
	private static final Logger logger = LoggerFactory.getLogger(SecurityService.class);
	
	/**
	 * pc端进行登入
	 * @param request
	 * @return
	 */
	public String login(UserLoginRequest request) {
		SystemToken token = null;
		UserResponse resp = null;
		if(StringUtils.isEmpty(request.getLoginName())) throw new ParamCheckException("用户名为空");
		token = new SystemToken(request.getLoginName(), request.getPassword());
		resp = (request.getLoginName().indexOf("@")!=-1)? 
				userService.queryUserByEmail(request.getLoginName())//根据邮箱查询
				:userService.queryUserByCellPhone(request.getLoginName());//根据手机号查询
		token.setTerminalType(request.getTerminalType());
		
		/** 获取当前的subject **/
		Subject currentUser = SecurityUtils.getSubject();
		Session session = null;
		try {
			currentUser.login(token);
			if(currentUser.isAuthenticated()){
				//获取session
				session = currentUser.getSession();
				if(resp!=null){
					//设置session中内容
					session.setAttribute("userName", resp.getUserName());
					session.setAttribute("userId", resp.getId());
					session.setAttribute("tenantId", resp.getTenantId());
					session.setAttribute("tenantName", resp.getTenantName());
					session.setAttribute("loginName", request.getLoginName());
					session.setAttribute("sessionId", session.getId().toString());
				}
				return session.getId().toString();
			}
			
		} catch (UnknownAccountException uae) {
			logger.info("当前用户不存在");
			
		} catch (IncorrectCredentialsException ice) {
			logger.info("用户名/密码不正确");
			
		} catch (LockedAccountException lae) {
			logger.info("登入验证未通过，账户已经锁定");
			
		} catch (ExcessiveAttemptsException eae) {
			logger.info("登入错误次数太多");
			
		} catch (AuthenticationException ae) {
			logger.info("用户名或者密码错误");
		}
		return null;
	}

	/**
	 * 用户登出
	 */
	public void logout() {
		if (SecurityUtils.getSubject().isAuthenticated()) {
			SecurityUtils.getSubject().logout();
		}
	}
}
