package com.kittydaddy.metadata.tenant.domain;

import java.util.Date;

/**
 * 
 * @author kitty daddy
 * 教师课程关系表
 */
public class TenantTeacherCourseEntity {
    /**
     * 主键
     */
	private Long id;
    
	/**
	 * 课程id
	 */
    private Long courseId;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 教师id
     */
    private Long teacherId;

    /**
     * 教师名称
     */
    private String teacherName;

    /**
     * 教师头像
     */
    private String avatar;

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

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName == null ? null : courseName.trim();
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
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