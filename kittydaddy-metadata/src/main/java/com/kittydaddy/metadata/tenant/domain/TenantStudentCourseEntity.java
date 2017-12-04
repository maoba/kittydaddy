package com.kittydaddy.metadata.tenant.domain;

import java.util.Date;

public class TenantStudentCourseEntity {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 关联课程id
     */
    private Long courseId;

    /**
     * 关联课程名字
     */
    private String courseName;

    /**
     * 学生id
     */
    private Long studentId;

    /**
     * 学生名称
     */
    private String studentName;

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

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName == null ? null : studentName.trim();
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