package com.kittydaddy.metadata.pvcontent.domain;

import java.util.Date;

public class PContentEntity {
    private String id;

    private String subOriginId;

    private String originId;

    private String source;

    private String title;

    private String subtitle;

    private String rate;

    private String tags;

    private String channel;

    private Integer shortFlag;

    private Integer episodeCount;

    private String directors;

    private String actors;

    private String year;

    private Integer lastSn;

    private Integer duration;

    private String area;

    private String originPubTime;

    private String language;

    private String genres;

    private Integer yesterPlayCount;

    private Integer weeklyPlayCount;

    private Integer totalPlayCount;

    private String imgSmallUrl;

    private String imgMediumUrl;

    private String imgLargeUrl;

    private Integer status;

    private Integer isPublish;

    private Integer isFree;

    private Date createTime;

    private Date updateTime;

    private String operateId;

    private Integer episodeExist;

    private String summary;

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

    public String getOperateId() {
        return operateId;
    }

    public void setOperateId(String operateId) {
        this.operateId = operateId == null ? null : operateId.trim();
    }

    public Integer getEpisodeExist() {
        return episodeExist;
    }

    public void setEpisodeExist(Integer episodeExist) {
        this.episodeExist = episodeExist;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }
}