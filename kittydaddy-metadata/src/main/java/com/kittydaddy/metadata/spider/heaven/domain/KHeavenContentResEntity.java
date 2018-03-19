package com.kittydaddy.metadata.spider.heaven.domain;

import java.util.Date;

public class KHeavenContentResEntity {
    /**
     * 主键id
     */
    private String id;

    /**
     * 电影标题
     */
    private String title;

    /**
     * 类别
     */
    private String genres;

    /**
     * 主角名称
     */
    private String roleName;

    /**
     * 描述信息
     */
    private String summary;

    /**
     * 豆瓣评分
     */
    private String rate;

    /**
     * 标题封面图
     */
    private String titlePic;

    /**
     * 内容剧照图
     */
    private String summaryPic;

    /**
     * 下载地址
     */
    private String downloadUrl;

    /**
     * 状态 1:生效 0：失效 -1：删除
     */
    private Integer status;

    /**
     * 发布状态 0：未发布 1：已发布
     */
    private Integer isPublish;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 发布时间
     */
    private Date publishTime;


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }


    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }


    public String getGenres() {
        return genres;
    }


    public void setGenres(String genres) {
        this.genres = genres == null ? null : genres.trim();
    }


    public String getRoleName() {
        return roleName;
    }


    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }


    public String getSummary() {
        return summary;
    }


    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }


    public String getRate() {
        return rate;
    }


    public void setRate(String rate) {
        this.rate = rate == null ? null : rate.trim();
    }


    public String getTitlePic() {
        return titlePic;
    }


    public void setTitlePic(String titlePic) {
        this.titlePic = titlePic == null ? null : titlePic.trim();
    }


    public String getSummaryPic() {
        return summaryPic;
    }


    public void setSummaryPic(String summaryPic) {
        this.summaryPic = summaryPic == null ? null : summaryPic.trim();
    }


    public String getDownloadUrl() {
        return downloadUrl;
    }


    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl == null ? null : downloadUrl.trim();
    }


    public Integer getStatus() {
        return status;
    }


    public void setStatus(Integer status) {
        this.status = status;
    }


    public Integer getIsPublish() {
        return isPublish;
    }


    public void setIsPublish(Integer isPublish) {
        this.isPublish = isPublish;
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


    public Date getPublishTime() {
        return publishTime;
    }


    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }
}