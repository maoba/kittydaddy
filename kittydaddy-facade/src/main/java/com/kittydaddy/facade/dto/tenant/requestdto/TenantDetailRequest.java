package com.kittydaddy.facade.dto.tenant.requestdto;
import java.util.Date;

/**
 * @author kitty daddy
 *
 */
public class TenantDetailRequest {
    private Long id;

    /**
     * 租户名称
     */
    private String name;

    /**
     * 租户的简单的介绍
     */
    private String description;

    /**
     * 地理位置
     */
    private String location;

    /**
     * 纬度
     */
    private Double latitude;

    /**
     * 经度
     */
    private Double longitude;

    /**
     * 公司注册地
     */
    private String registerLocal;

    /**
     * 教职工总人数
     */
    private Integer teacherNum;

    /**
     * 创建时间 
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 详细描述信息
     */
    private String detailDesc;

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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getRegisterLocal() {
        return registerLocal;
    }

    public void setRegisterLocal(String registerLocal) {
        this.registerLocal = registerLocal == null ? null : registerLocal.trim();
    }

    public Integer getTeacherNum() {
        return teacherNum;
    }

    public void setTeacherNum(Integer teacherNum) {
        this.teacherNum = teacherNum;
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

    public String getDetailDesc() {
        return detailDesc;
    }

    public void setDetailDesc(String detailDesc) {
        this.detailDesc = detailDesc == null ? null : detailDesc.trim();
    }
}
