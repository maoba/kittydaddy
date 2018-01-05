package com.kittydaddy.metadata.system.domain;
import java.io.Serializable;
import java.util.Date;
/**
 * @author kitty daddy
 * 系统用户
 */
@SuppressWarnings("serial")
public class UserEntity implements Serializable{
    
	/**
	 * 主键
	 */
    private String id;

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
    private String tenantId;

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
    * 状态  -1:表示删除 0：表示失效  1：表示正常状态
    */
    private Integer status;
    
    /**
     * 终端类型
     */
    private Integer terminalType;
    
    /**
     * 性别 0-男 1-女
     */
    private Integer sex;
    
    /**
     * 登入方式
     *  0：表示手机登入
     *  1：表示邮箱登入
     */
    private Integer loginType;
    
    /**
     * 省份
     */
    private String province;
    
    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String area;
    
    /**
     * 爱好
     */
    private String hobby;
    
    /**
     * 出生日期
     */
    private Date birthday;
    
    /**
     * 真实姓名
     */
    private String realName;
    
    public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

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

	public Integer getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(Integer terminalType) {
		this.terminalType = terminalType;
	}

    public String getId() {
		return id;
	}

	public void setId(String id) {
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

    public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
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