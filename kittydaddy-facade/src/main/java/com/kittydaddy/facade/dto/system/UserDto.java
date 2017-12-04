package com.kittydaddy.facade.dto.system;

import java.util.Date;

/**
 * @author kitty daddy
 * 用户dto
 */
public class UserDto {
	/**
	 * 主键id
	 */
	private Long id;
	
    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户密码
     */
    private String userPwd;

    /**
     * 租户id
     */
    private Long tenantId;

   /**
    * 租户名称 
    */
    private String tenantName;

    /**
     * 手机号码
     */
    private String cellPhoneNum;

   /**
    * 邮箱
    */
    private String email;

    /**
     * 盐
     */
    private String salt;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 职位信息
     */
    private String position;

   /**
    * 状态  -1:表示删除 0：表示失效  1：表示正常状态  2：表示锁定状态
    */
    private Integer status;
    
    /**
     * 登录方式
     */
    private Integer loginType;
    
    /**
     * 性别    
     */
    private Integer sex;
    
    public Integer getLoginType() {
		return loginType;
	}

	public void setLoginType(Integer loginType) {
		this.loginType = loginType;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Long getId() {
	    return id;
    }

    public void setId(Long id) {
	    this.id = id;
    }

	public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd == null ? null : userPwd.trim();
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName == null ? null : tenantName.trim();
    }

    public String getCellPhoneNum() {
        return cellPhoneNum;
    }

    public void setCellPhoneNum(String cellPhoneNum) {
        this.cellPhoneNum = cellPhoneNum == null ? null : cellPhoneNum.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
