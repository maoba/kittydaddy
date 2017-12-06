package com.kittydaddy.metadata.tenant.domain;
import java.util.Date;
/**
 * @author kitty daddy
 * 租户实体类
 */
public class TenantEntity {
    
	/**
	 * 主键id
	 */
	private String id;

    /**
     * 学校名称
     */
    private String name;

    /**
     * 学校简单介绍
     */
    private String description;

    /**
     * 地理位置
     */
    private String location;
    
    /**
     * 关联详细租户的Id
     */
    private String detailId;

    /**
     * 状态 -1：删除 0：失效 1：生效
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

    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDetailId() {
		return detailId;
	}

	public void setDetailId(String detailId) {
		this.detailId = detailId;
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