package com.kittydaddy.metadata.tenant.domain;

import java.util.Date;
/**
 * @author kitty daddy
 * 学生实体
 */
public class TenantStudentEntity {
	/**
	 * 主键id
	 */
    private Long id;

    /**
     * 学生名称
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性别  0-男  1-女
     */
    private Integer sex;

    /**
     * 家庭住址
     */
    private String address;

   /**
    * 学生类型：0-少儿 1-青少年 2-成人
    */
    private Integer studentType;

    /**
     * 租户id
     */
    private Long tenantId;

    /**
     * 班级id
     */
    private Long classId;

    /**
     * 班级名称
     */
    private String className;

    /**
     * 入校时间
     */
    private Date startTime;

    /**
     * 离校时间
     */
    private Date finishTime;

    /**
     *  -1:删除 0：离校  1：在校
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Integer getStudentType() {
        return studentType;
    }

    public void setStudentType(Integer studentType) {
        this.studentType = studentType;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className == null ? null : className.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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