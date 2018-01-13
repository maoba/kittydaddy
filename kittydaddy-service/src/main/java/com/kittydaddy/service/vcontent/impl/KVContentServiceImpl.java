package com.kittydaddy.service.vcontent.impl;

import java.util.Date;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kittydaddy.common.constant.RedisKeyConstant;
import com.kittydaddy.common.enums.IsFreeEnum;
import com.kittydaddy.common.enums.PublishStatusEnum;
import com.kittydaddy.common.enums.ShortFlagEnum;
import com.kittydaddy.common.enums.StatusEnum;
import com.kittydaddy.common.utils.KCollectionUtils;
import com.kittydaddy.common.utils.KHttpClientUtil;
import com.kittydaddy.common.utils.KJSONPParser;
import com.kittydaddy.common.utils.KStringUtils;
import com.kittydaddy.metadata.util.RedisUtil;
import com.kittydaddy.metadata.vcontent.dao.KVContentEntityMapper;
import com.kittydaddy.metadata.vcontent.dao.KVContentItemEntityMapper;
import com.kittydaddy.metadata.vcontent.dao.KVContentSourceEntityMapper;
import com.kittydaddy.metadata.vcontent.domain.KVContentEntity;
import com.kittydaddy.metadata.vcontent.domain.KVContentItemEntity;
import com.kittydaddy.metadata.vcontent.domain.KVContentSourceEntity;
import com.kittydaddy.service.vcontent.KVContentService;

@Service
public class KVContentServiceImpl implements KVContentService{
	private static final Logger logger = LoggerFactory.getLogger(KVContentServiceImpl.class);
	@Autowired
	private KVContentEntityMapper kvContentMapper;
	
	@Autowired
	private KVContentItemEntityMapper kvContentItemMapper;
	
	@Autowired
	private KVContentSourceEntityMapper kvContentSourceMapper;
	
	@Value("${kitty.video.content.detail}")
	private String detailUrl;
	
	@Value("${kitty.video.content.item.source}")
	private String itemSourceUrl;
	
	@Autowired
	private RedisUtil redisUtil;

	@Override
	public void executeCollectVideoJobService(Map<String, Object> map) {
    	for(Integer subOriginId = 1; subOriginId<999999999; subOriginId++){
    		Object obj = redisUtil.get(RedisKeyConstant.SUBORIGIN_INDEX_PREFIX+subOriginId);
    		if(obj != null) continue;
    		if(!this.executeCollectSingleVideoService(subOriginId.toString())) continue;
    		redisUtil.set(RedisKeyConstant.SUBORIGIN_INDEX_PREFIX+subOriginId, subOriginId);
    	}
    }
	
	//获取详细的视频信息
	private String achieveDetailRequestUrl(String requestUrl,String id){
		StringBuffer sb = new StringBuffer();
		String finalDetailUrl = sb.append(requestUrl).append("?id="+id+"&platform=1&callback=jsonp5").toString();
		return finalDetailUrl;
	}
	
	//获取源、剧集播放地址url
	private String achieveItemSourceRequestUrl(String requestUrl,String contentId,String srcId,Integer size){
		StringBuffer sb = new StringBuffer();
		String finalItemSourceUrl = sb.append(requestUrl).append("?id="+contentId).append("&src_id="+srcId+"&start=0").append("&size=" + size).toString();
		return finalItemSourceUrl;
	}

	@Override
	public boolean executeCollectSingleVideoService(String subOriginId) {
		try{
    		String respDetailContent = KHttpClientUtil.doGet(achieveDetailRequestUrl(detailUrl,subOriginId.toString()));
    		JSONObject detailRespObject = KJSONPParser.parseJSONP(respDetailContent);
    		if(detailRespObject==null) return false;
    		JSONObject videoDetailObj = detailRespObject.getJSONObject("data").getJSONObject("video");
    		
    		KVContentEntity kvContent = kvContentMapper.queryKvContentBySubOriginId(subOriginId.toString());
			if(kvContent!=null){
				 logger.info(String.format("影视'%s'已存在！", kvContent));
				 return false;
			}
			
			JSONArray imageArrays = videoDetailObj.getJSONArray("images");
			if(KCollectionUtils.isEmpty(imageArrays)) logger.info("不存在相关的封面照片");
    		
			KVContentEntity content = new KVContentEntity();
			content.setYesterPlayCount(0);
			content.setYear(videoDetailObj.getString("year"));
			content.setWeeklyPlayCount(0);
			content.setTotalPlayCount(0);
			content.setTitle(videoDetailObj.getString("title"));
			content.setTags(videoDetailObj.getString("tags"));
			content.setSummary(videoDetailObj.getString("summary"));
			content.setSubtitle(videoDetailObj.getString("sub_title"));
			content.setSubOriginId(subOriginId.toString());
			content.setStatus(StatusEnum.VALID.getValue());
			content.setShortFlag(ShortFlagEnum.LONG.getValue());
			content.setRate(videoDetailObj.getString("rating"));
			content.setOriginPubTime(videoDetailObj.getString("pub_date"));
			content.setOriginId(videoDetailObj.getString("douban_id"));
			content.setLastSn(videoDetailObj.getInteger("last_sn"));
			content.setLanguage(videoDetailObj.getString("lang"));
			content.setIsPublish(PublishStatusEnum.NO.getValue());
			content.setIsFree(IsFreeEnum.YES.getValue());
			for(int j = 0;j<imageArrays.size();j++){
				JSONObject jsonObject = imageArrays.getJSONObject(j);
				String imageType = jsonObject.getString("type");
				String imageUrl = jsonObject.getString("url");
				if("LARGE".equals(imageType)){
					content.setImgLargeUrl(imageUrl);
				}else if("SMALL".equals(imageType)){
					content.setImgSmallUrl(imageUrl);
				}else if("MEDIUM".equals(imageType)){
					content.setImgMediumUrl(imageUrl);
				}
			}
			content.setGenres(videoDetailObj.getString("genres"));
			content.setEpisodeCount(videoDetailObj.getInteger("episode_count"));
			content.setDuration(videoDetailObj.getInteger("duration"));
			content.setDirectors(videoDetailObj.getString("directors"));
			content.setCreateTime(new Date());
			content.setChannel(videoDetailObj.getString("channel"));
			content.setArea(videoDetailObj.getString("area"));
			content.setActors(videoDetailObj.getString("actors"));
			logger.info("**************开始获取:"+content.getChannel()+"————"+content.getTitle()+"***********");
			kvContentMapper.insert(content);
			
			//获取内容信息Id
			String contentId = content.getId();
			JSONArray collections = videoDetailObj.getJSONArray("collections");
			
			if(KCollectionUtils.isEmpty(collections)){
				logger.info(String.format("影视：%s不存在播放地址",content.getTitle()));
				return false;
			}
			for(int k=0; k<collections.size(); k++){
				String srcId = collections.getJSONObject(k).getString("src_id");
				String srcName = collections.getJSONObject(k).getString("src_name");
				Integer isFree = collections.getJSONObject(k).getInteger("free");
				
				String finalItemSourceUrl = achieveItemSourceRequestUrl(itemSourceUrl,subOriginId.toString(),srcId,videoDetailObj.getInteger("episode_count"));
				JSONObject jsonObj = JSON.parseObject(KHttpClientUtil.doGet(finalItemSourceUrl));
				try{
					JSONArray itemSourceJsonArray = jsonObj.getJSONObject("data").getJSONArray("list");
					if(KCollectionUtils.isNotEmpty(itemSourceJsonArray)){
						for(int o = 0;o<itemSourceJsonArray.size();o++){
							String itemTitle = itemSourceJsonArray.getJSONObject(o).getString("title");
							String itemPeriod = itemSourceJsonArray.getJSONObject(o).getString("period");
							if(KStringUtils.isEmpty(itemTitle)||KStringUtils.isEmpty(itemPeriod)) continue;
							
							KVContentItemEntity itemEntity = kvContentItemMapper.queryItemTitleAndPeriod(itemTitle,itemPeriod);
							String contentItemId = "";
							String itemSn = "";
							if(itemEntity == null){
								KVContentItemEntity kvContentItemEntity = new KVContentItemEntity();
								kvContentItemEntity.setContentId(contentId);
								kvContentItemEntity.setCreateTime(new Date());
								kvContentItemEntity.setItemChannel(videoDetailObj.getString("channel"));
								kvContentItemEntity.setItemPeriod(itemSourceJsonArray.getJSONObject(o).getString("period"));
								kvContentItemEntity.setItemSn(itemSourceJsonArray.getJSONObject(o).getInteger("item_sn"));
								kvContentItemEntity.setStatus(StatusEnum.VALID.getValue());
								kvContentItemEntity.setItemTitle(itemSourceJsonArray.getJSONObject(o).getString("title"));
								logger.info("*************开始获取："+content.getChannel()+":"+content.getTitle()+"第"+kvContentItemEntity.getItemSn()+"集(期)**********");
								kvContentItemMapper.insert(kvContentItemEntity);
								
								contentItemId = kvContentItemEntity.getId();
								itemSn = kvContentItemEntity.getItemSn().toString();
							}else{
								contentItemId = itemEntity.getId();
								itemSn = itemEntity.getItemSn().toString();
							}
							
							KVContentSourceEntity contentSource = new KVContentSourceEntity();
							String playUrl = itemSourceJsonArray.getJSONObject(o).getString("url");
							String duration = itemSourceJsonArray.getJSONObject(o).getString("duration");
							String imageUrl = itemSourceJsonArray.getJSONObject(o).getString("img_url");
							
							contentSource.setCreateTime(new Date());
							contentSource.setRelativeType("k_video_content_item");
							contentSource.setRelativeId(contentItemId);
							contentSource.setDuration(duration);
							contentSource.setImageUrl(imageUrl);
							contentSource.setIsFree(isFree);
							contentSource.setSourceId(srcId);
							contentSource.setPlayUrl(playUrl);
							contentSource.setSource(srcName);
							contentSource.setStatus(StatusEnum.VALID.getValue());
							kvContentSourceMapper.insert(contentSource);
							logger.info("*************"+content.getChannel()+":"+content.getTitle()+"第"+ itemSn +"集(期)**********");
						}
					}
					logger.info("*****影视:"+content.getTitle()+"获取成功！originId为:"+ subOriginId +"*******");
				}catch(Exception e){
					logger.error("影视："+content.getTitle()+"的'"+srcName+"'源获取失败！");
					continue;
				}
			}
    	}catch(Exception e){
    		logger.info("*********发生未知错误**********");
    	}
		return true;
	}
}
