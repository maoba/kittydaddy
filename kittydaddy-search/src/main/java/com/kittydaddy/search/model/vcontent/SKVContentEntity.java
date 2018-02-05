package com.kittydaddy.search.model.vcontent;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "kvcontent", type = "content")
public class SKVContentEntity {
    /**
     * 编码Id
     */
	@Id
	private String id;

    /**
     * 第三方标识id
     */
    private String subOriginId;

    /**
     * 原始Id标识
     */
    private String originId;

    /**
     * 来源
     */
    private String source;

    /**
     * 标题
     */
    private String title;

    /**
     * 二级标题
     */
    private String subtitle;

    /**
     * 评分
     */
    private String rate;

    /**
     * 标签
     */
    private String tags;

    /**
     * 视频类别（电影、电视剧、综艺、动漫、音乐、微电影、搞笑、直播、娱乐、新闻、亲子、游戏、体育）
     */
    private String channel;

    /**
     * 0:表示长视频 1:表示短视频
     */
    private Integer shortFlag;

    /**
     * 总剧集数
     */
    private Integer episodeCount;

    /**
     * 导演
     */
    private String directors;

    /**
     * 演员
     */
    private String actors;

    /**
     * 年份
     */
    private String year;

    /**
     * 最近更新集数
     */
    private Integer lastSn;

    /**
     * 时长
     */
    private Integer duration;

    /**
     * 地区
     */
    private String area;

    /**
     * 影片发布时间
     */
    private String originPubTime;

    /**
     * 语言
     */
    private String language;

    /**
     * 题材
     */
    private String genres;

    /**
     * 年播放次数
     */
    private Integer yesterPlayCount;

    /**
     * 周播放次数
     */
    private Integer weeklyPlayCount;

    /**
     * 总播放次数
     */
    private Integer totalPlayCount;

    /**
     * 封面图片，小
     */
    private String imgSmallUrl;

    /**
     * 封面图片,中等
     */
    private String imgMediumUrl;

    /**
     * 封面图片,大图
     */
    private String imgLargeUrl;

    /**
     * -1：删除 0:失效 1:生效
     */
    private Integer status;

    /**
     * 是否已经发布
     */
    private Integer isPublish;
    
    /**
     * 是否存在剧集 0:不存在 1：存在
     */
    private Integer episodeExist;

    /**
     * 描述
     */
    private String summary;

    /**
     * 是否收费 1：表示不收费 2：表示收费
     */
    private Integer isFree;

    /**
     * 记录创建时间
     */
    private Date createTime;

    /**
     * 记录更新时间
     */
    private Date updateTime;

    /**
     * 记录发布时间
     */
    private Date publishTime;
   
    /**
     * 操作人id
     */
    private String operateId;
 
    
    
    public Integer getEpisodeExist() {
		return episodeExist;
	}

	public void setEpisodeExist(Integer episodeExist) {
		this.episodeExist = episodeExist;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getSubOriginId() {
        return subOriginId;
    }

    public void setSubOriginId(String subOriginId) {
        this.subOriginId = subOriginId == null ? null : subOriginId.trim();
    }

    public String getOriginId() {
        return originId;
    }

    public void setOriginId(String originId) {
        this.originId = originId == null ? null : originId.trim();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle == null ? null : subtitle.trim();
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate == null ? null : rate.trim();
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags == null ? null : tags.trim();
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel == null ? null : channel.trim();
    }

    public Integer getShortFlag() {
        return shortFlag;
    }

    public void setShortFlag(Integer shortFlag) {
        this.shortFlag = shortFlag;
    }

    public Integer getEpisodeCount() {
        return episodeCount;
    }

    public void setEpisodeCount(Integer episodeCount) {
        this.episodeCount = episodeCount;
    }

    public String getDirectors() {
        return directors;
    }

    public void setDirectors(String directors) {
        this.directors = directors == null ? null : directors.trim();
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors == null ? null : actors.trim();
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year == null ? null : year.trim();
    }

    public Integer getLastSn() {
        return lastSn;
    }

    public void setLastSn(Integer lastSn) {
        this.lastSn = lastSn;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public String getOriginPubTime() {
        return originPubTime;
    }

    public void setOriginPubTime(String originPubTime) {
        this.originPubTime = originPubTime == null ? null : originPubTime.trim();
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language == null ? null : language.trim();
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres == null ? null : genres.trim();
    }

    public Integer getYesterPlayCount() {
        return yesterPlayCount;
    }

    public void setYesterPlayCount(Integer yesterPlayCount) {
        this.yesterPlayCount = yesterPlayCount;
    }

    public Integer getWeeklyPlayCount() {
        return weeklyPlayCount;
    }

    public void setWeeklyPlayCount(Integer weeklyPlayCount) {
        this.weeklyPlayCount = weeklyPlayCount;
    }

    public Integer getTotalPlayCount() {
        return totalPlayCount;
    }

    public void setTotalPlayCount(Integer totalPlayCount) {
        this.totalPlayCount = totalPlayCount;
    }

    public String getImgSmallUrl() {
        return imgSmallUrl;
    }

    public void setImgSmallUrl(String imgSmallUrl) {
        this.imgSmallUrl = imgSmallUrl == null ? null : imgSmallUrl.trim();
    }

    public String getImgMediumUrl() {
        return imgMediumUrl;
    }

    public void setImgMediumUrl(String imgMediumUrl) {
        this.imgMediumUrl = imgMediumUrl == null ? null : imgMediumUrl.trim();
    }

    public String getImgLargeUrl() {
        return imgLargeUrl;
    }

    public void setImgLargeUrl(String imgLargeUrl) {
        this.imgLargeUrl = imgLargeUrl == null ? null : imgLargeUrl.trim();
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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }

    public Integer getIsFree() {
        return isFree;
    }

    public void setIsFree(Integer isFree) {
        this.isFree = isFree;
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

    public String getOperateId() {
        return operateId;
    }

    public void setOperateId(String operateId) {
        this.operateId = operateId == null ? null : operateId.trim();
    }
}