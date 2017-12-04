package com.kittydaddy.metadata.tenant.domain;

import java.util.Date;

/**
 * @author kitty daddy
 * 关联家人表
 */
public class TenantFamilyEntity {
    /**
     * 主键id
     */
	private Long id;

    /**
     * 家庭成员名字
     */
    private String memberName;

    /**
     * 家庭成员类型  0-父亲  1-母亲  3-配偶
     */
    private Integer memberType;

    /**
     * 家庭成员联系方式
     */
    private String memberContact;

    /**
     * 家庭固定电话
     */
    private String familyTel;

    /**
     * 家庭地址
     */
    private String address;

    /**
     * 家庭关联类型  0-关联学生 1-关联教师
     */
    private Integer familyType;

    /**
     * 关联id
     */
    private Long relativeId;

    /**
     * 关联名字
     */
    private String relativeName;

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

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName == null ? null : memberName.trim();
    }

    public Integer getMemberType() {
        return memberType;
    }

    public void setMemberType(Integer memberType) {
        this.memberType = memberType;
    }

    public String getMemberContact() {
        return memberContact;
    }

    public void setMemberContact(String memberContact) {
        this.memberContact = memberContact == null ? null : memberContact.trim();
    }

    public String getFamilyTel() {
        return familyTel;
    }

    public void setFamilyTel(String familyTel) {
        this.familyTel = familyTel == null ? null : familyTel.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Integer getFamilyType() {
        return familyType;
    }

    public void setFamilyType(Integer familyType) {
        this.familyType = familyType;
    }

    public Long getRelativeId() {
        return relativeId;
    }

    public void setRelativeId(Long relativeId) {
        this.relativeId = relativeId;
    }

    public String getRelativeName() {
        return relativeName;
    }

    public void setRelativeName(String relativeName) {
        this.relativeName = relativeName == null ? null : relativeName.trim();
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