package com.kittydaddy.search.model.vcontent;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @author kitty daddy
 * 源地址实体类
 */
@Document(indexName = "kvcontentsource", type = "contentsource")
public class SKVContentSourceEntity {
	/**
	 * 编码id
	 */
	@Id
    private String id;
    
    /**
     * 播放url地址
     */
    private String playUrl;

    /**
     * 源id
     */
    private String sourceId;

    /**
     * 来源名称
     */
    private String source;

    /**
     * 是否免费 1:免费 2:收费
     */
    private Integer isFree;

    /**
     * 关联id
     */
    private String relativeId;

    /**
     * 关联类型（电影、电视剧等等）
     */
    private String relativeType;

    /**
     * 剧集图片url
     */
    private String imageUrl;

    /**
     * 状态 -1：删除 0：失效  1：生效
     */
    private Integer status;
    
    /**
     * 时长
     */
    private String duration;

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

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl == null ? null : playUrl.trim();
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId == null ? null : sourceId.trim();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public Integer getIsFree() {
        return isFree;
    }

    public void setIsFree(Integer isFree) {
        this.isFree = isFree;
    }

    public String getRelativeId() {
        return relativeId;
    }

    public void setRelativeId(String relativeId) {
        this.relativeId = relativeId == null ? null : relativeId.trim();
    }

    public String getRelativeType() {
        return relativeType;
    }

    public void setRelativeType(String relativeType) {
        this.relativeType = relativeType == null ? null : relativeType.trim();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration == null ? null : duration.trim();
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