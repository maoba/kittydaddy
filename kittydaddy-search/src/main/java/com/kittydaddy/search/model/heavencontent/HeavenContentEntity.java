package com.kittydaddy.search.model.heavencontent;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "heavencontent", type = "heavencontent")
public class HeavenContentEntity {
    /**
     * 主键id
     */
    @Id
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


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getTitlePic() {
        return titlePic;
    }

    public void setTitlePic(String titlePic) {
        this.titlePic = titlePic;
    }

    public String getSummaryPic() {
        return summaryPic;
    }

    public void setSummaryPic(String summaryPic) {
        this.summaryPic = summaryPic;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
