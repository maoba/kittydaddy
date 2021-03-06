package com.kittydaddy.metadata.pvcontent.domain;

import java.util.Date;

public class PContentItemEntity {
    private String id;

    private String itemChannel;

    private String itemTitle;

    private Integer itemSn;

    private String itemPeriod;

    private String itemSummary;

    private Integer status;

    private String contentId;

    private Date createTime;

    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getItemChannel() {
        return itemChannel;
    }

    public void setItemChannel(String itemChannel) {
        this.itemChannel = itemChannel == null ? null : itemChannel.trim();
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle == null ? null : itemTitle.trim();
    }

    public Integer getItemSn() {
        return itemSn;
    }

    public void setItemSn(Integer itemSn) {
        this.itemSn = itemSn;
    }

    public String getItemPeriod() {
        return itemPeriod;
    }

    public void setItemPeriod(String itemPeriod) {
        this.itemPeriod = itemPeriod == null ? null : itemPeriod.trim();
    }

    public String getItemSummary() {
        return itemSummary;
    }

    public void setItemSummary(String itemSummary) {
        this.itemSummary = itemSummary == null ? null : itemSummary.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId == null ? null : contentId.trim();
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