package com.kittydaddy.facade.dto.tenant;

import java.util.Date;

/**
 * @author kitty daddy
 * 租户教师dto
 */
public class TenantTeacherDto {
	/**
	 * 主键id
	 */
    private Long id;
    
    /**
     * 教师名称
     */
    private String name;
    
    /**
     * 手机号码
     */
    private String cellphone;

    /**
     * 固定电话号码
     */
    private String telephone;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 性别  0：男  1：女
     */
    private Integer sex;

    /**
     * 职位信息
     */
    private String position;

    /**
     * 户籍地址
     */
    private String homeAddress;

    /**
     * 现居地址
     */
    private String nowAddress;

    /**
     * 经验 （单位:年）
     */
    private Integer experience;

    /**
     * 租户id
     */
    private Long tenantId;

    /**
     * 头像地址信息
     */
    private String avatar;

    /**
     * 学历
     */
    private String qualifications;

    /**
     * 毕业院校
     */
    private String graduateInstitutions;

    /**
     * 描述信息
     */
    private String description;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 状态 -1:删除 0：离职 1：在职
     */
    private Integer status;
    
    /**
     * 邮箱地址
     */
    private String email;

    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getStatus() {
		return status;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone == null ? null : cellphone.trim();
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress == null ? null : homeAddress.trim();
    }

    public String getNowAddress() {
        return nowAddress;
    }

    public void setNowAddress(String nowAddress) {
        this.nowAddress = nowAddress == null ? null : nowAddress.trim();
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    public String getQualifications() {
        return qualifications;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications == null ? null : qualifications.trim();
    }

    public String getGraduateInstitutions() {
        return graduateInstitutions;
    }

    public void setGraduateInstitutions(String graduateInstitutions) {
        this.graduateInstitutions = graduateInstitutions == null ? null : graduateInstitutions.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
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
}
