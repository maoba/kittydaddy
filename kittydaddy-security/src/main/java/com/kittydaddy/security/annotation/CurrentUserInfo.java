package com.kittydaddy.security.annotation;

/**
 * @author kitty daddy
 * session中存储的内容信息
 */
public class CurrentUserInfo {

	/**
	 * 用户id
	 */
    private String userId;
    
    /**
     * 用户名称
     */
    private String userName;
    
    /**
     * 登录名称
     */
    private String loginName;
    
    /**
     * 租户Id
     */
    private String tenantId;
    
    /**
     * 租户名称
     */
    private String tenantName;

    /**
     * sessionId
     */
    private String sessionId;
    
    
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}
}
