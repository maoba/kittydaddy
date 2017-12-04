package com.kittydaddy.facade.dto.system.request;


/**
 * @author kitty daddy
 * 用户登入请求
 */
public class UserLoginRequest {
	/**
	 * 手机号码
	 */
    private String loginName;
    
    /**
     * 密码
     */
    private String password;
    
    /**
     * 登入的终端类型
     * 0:pc端  1:手机端
     */
    private Integer terminalType;
    
    /**
     * 登入类型  
     * 0:手机登入 1: 邮箱登入
     */
    private Integer loginType;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(Integer terminalType) {
		this.terminalType = terminalType;
	}

	public Integer getLoginType() {
		return loginType;
	}

	public void setLoginType(Integer loginType) {
		this.loginType = loginType;
	}
}
