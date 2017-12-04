package com.kittydaddy.metadata.tenant.domain;

import java.util.Date;

/**
 * @author kitty daddy
 * 课程表
 */
public class TenantCourseEntity {
    /**
     * 主键
     */
	private Long id;
    
	/**
	 * 课程名称
	 */
    private Long name;
     
    /**
     * 适合对象
     */
    private String fit;
    
    /**
     * 课程目标
     */
    private String target;

    /**
     * 课程特色
     */
    private String feature;
    
    /**
     * 租户id
     */
    private Long tenantId;
    
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

    public Long getName() {
        return name;
    }

    public void setName(Long name) {
        this.name = name;
    }

    public String getFit() {
        return fit;
    }

    public void setFit(String fit) {
        this.fit = fit == null ? null : fit.trim();
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target == null ? null : target.trim();
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature == null ? null : feature.trim();
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
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