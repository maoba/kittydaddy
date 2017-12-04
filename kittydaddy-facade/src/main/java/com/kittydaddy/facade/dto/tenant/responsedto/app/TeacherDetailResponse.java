package com.kittydaddy.facade.dto.tenant.responsedto.app;

/**
 * @author kitty daddy
 * 教师详情
 */
public class TeacherDetailResponse {
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
     * 经验 （单位:年）
     */
    private Integer experience;

    /**
     * 头像地址信息
     */
    private String avatar;

    /**
     * 描述信息
     */
    private String description;

    /**
     * 邮箱地址
     */
    private String email;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Integer getExperience() {
		return experience;
	}

	public void setExperience(Integer experience) {
		this.experience = experience;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
