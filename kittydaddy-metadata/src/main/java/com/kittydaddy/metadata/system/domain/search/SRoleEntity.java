package com.kittydaddy.metadata.system.domain.search;

import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "role", type = "system_role")
public class SRoleEntity {
	/**
	 * 主键Id
	 */
	@Id
    private String id;
    
    /**
     * 角色名称
     */
    private String roleName;
    
    /**
     * 租户id
     */
    private String tenantId;
    
    /**
     * 角色编码
     */
    private String roleCode;

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

    private SRoleEntity(){
    	
    }

	public SRoleEntity(String id, String roleName, String tenantId, String roleCode, String description,
			Date createTime, Date updateTime) {
		super();
		this.id = id;
		this.roleName = roleName;
		this.tenantId = tenantId;
		this.roleCode = roleCode;
		this.description = description;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}
    
	@Override
    public String toString() {
        return "Role{" +
                "id='" + id + '\'' +
                ", roleName='" + roleName + '\'' +
                ", tenantId='" + tenantId + '\'' +
                ", roleCode='" + roleCode + '\'' +
                '}';
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
