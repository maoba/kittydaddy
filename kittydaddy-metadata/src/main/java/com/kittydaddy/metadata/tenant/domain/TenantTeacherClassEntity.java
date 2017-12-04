package com.kittydaddy.metadata.tenant.domain;

import java.util.Date;

/**
 * @author kitty daddy
 * 教师班级关系表
 */
public class TenantTeacherClassEntity {
	/**
	 * 主键id
	 */
    private Long id;

    /**
     * 关联教师的id
     */
    private Long teacherId;

    /**
     * 关联教师名字
     */
    private String teacherName;

    /**
     * 班级id
     */
    private Long classId;

    /**
     * 教师职位信息
     */
    private String position;

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

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName == null ? null : teacherName.trim();
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
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