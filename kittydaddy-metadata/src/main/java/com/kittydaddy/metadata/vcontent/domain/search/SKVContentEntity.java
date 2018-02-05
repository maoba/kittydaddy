package com.kittydaddy.metadata.vcontent.domain.search;
import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "kvcontent", type = "kvcontents")
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
 
    private  SKVContentEntity(){
    	
    }

	public SKVContentEntity(String id, String subOriginId, String originId, String source, String title,
			String subtitle, String rate, String tags, String channel, Integer shortFlag, Integer episodeCount,
			String directors, String actors, String year, Integer lastSn, Integer duration, String area,
			String originPubTime, String language, String genres, Integer yesterPlayCount, Integer weeklyPlayCount,
			Integer totalPlayCount, String imgSmallUrl, String imgMediumUrl, String imgLargeUrl, Integer status,
			Integer isPublish, Integer episodeExist, String summary, Integer isFree, Date createTime, Date updateTime,
			Date publishTime, String operateId) {
		super();
		this.id = id;
		this.subOriginId = subOriginId;
		this.originId = originId;
		this.source = source;
		this.title = title;
		this.subtitle = subtitle;
		this.rate = rate;
		this.tags = tags;
		this.channel = channel;
		this.shortFlag = shortFlag;
		this.episodeCount = episodeCount;
		this.directors = directors;
		this.actors = actors;
		this.year = year;
		this.lastSn = lastSn;
		this.duration = duration;
		this.area = area;
		this.originPubTime = originPubTime;
		this.language = language;
		this.genres = genres;
		this.yesterPlayCount = yesterPlayCount;
		this.weeklyPlayCount = weeklyPlayCount;
		this.totalPlayCount = totalPlayCount;
		this.imgSmallUrl = imgSmallUrl;
		this.imgMediumUrl = imgMediumUrl;
		this.imgLargeUrl = imgLargeUrl;
		this.status = status;
		this.isPublish = isPublish;
		this.episodeExist = episodeExist;
		this.summary = summary;
		this.isFree = isFree;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.publishTime = publishTime;
		this.operateId = operateId;
	}  
    
    @Override
    public String toString() {
    	// TODO Auto-generated method stub
    	return super.toString();
    }
}