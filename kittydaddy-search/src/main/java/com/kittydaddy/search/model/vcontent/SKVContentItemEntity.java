package com.kittydaddy.search.model.vcontent;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * 剧集
 * @author kitty daddy
 */
@Document(indexName = "kvcontentitem", type = "contentitem")
public class SKVContentItemEntity {
	/**
	 * 编码id
	 */
	@Id
    private String id;

    /**
     * 剧集类型
     */
    private String itemChannel;

    /**
     * 剧集标题
     */
    private String itemTitle;

    /**
     * 最近更新剧集
     */
    private Integer itemSn;
   
    /**
     * 剧集阶段
     */
    private String itemPeriod;

    /**
     * 剧集描述信息
     */
    private String itemSummary;

    /**
     * 状态 -1:删除 0:失效 1:生效
     */
    private Integer status;

    /**
     * 关联内容id
     */
    private String contentId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
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