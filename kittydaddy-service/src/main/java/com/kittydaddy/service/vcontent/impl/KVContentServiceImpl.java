package com.kittydaddy.service.vcontent.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kittydaddy.common.constant.Constants;
import com.kittydaddy.common.constant.RedisKeyConstant;
import com.kittydaddy.common.enums.EpisodeExistEnum;
import com.kittydaddy.common.enums.IsFreeEnum;
import com.kittydaddy.common.enums.PublishStatusEnum;
import com.kittydaddy.common.enums.ShortFlagEnum;
import com.kittydaddy.common.enums.ShortGenresEnum;
import com.kittydaddy.common.enums.SourceEnum;
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
	
	@Value("${kitty.video.short.content.list}")
	private String shortListUrl;
	
	@Autowired
	private RedisUtil redisUtil;
	
	//设置起始页
	private Integer start = 0;

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
	@Transactional
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
			
			
			//获取内容信息Id
			String contentId = content.getId();
			JSONArray collections = videoDetailObj.getJSONArray("collections");
			
			if(KCollectionUtils.isEmpty(collections)){
				logger.info(String.format("影视：%s不存在播放地址",content.getTitle()));
				content.setEpisodeExist(EpisodeExistEnum.NO.getValue());
				kvContentMapper.insert(content);
				return false;
			}else{
				//存在剧集
				content.setEpisodeExist(EpisodeExistEnum.YES.getValue());
				kvContentMapper.insert(content);
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

	@Override
	public void executeCollectShortVideoJobService(Map<String, Object> map) {
		 while(start >= 0){
   		  if(execute()){ 
   			  start += 100;
             }else{
           	  break;
           }
   	  }
	}
	
	 //获取最终列表请求的url
		private String achieveShortListReqeustUrl(String requestUrl,String genre,String source) {
			StringBuffer sb = new StringBuffer();
			String finalRequestUrl = sb.append(requestUrl)
					.append("?genres="+genre+"&size=100&")
					.append("start="+start+"")
					.append("&source="+source)
					.append("&order=3").toString();
			return finalRequestUrl;
		}
		
		
		private boolean execute(){
			for(SourceEnum sourceEnum : SourceEnum.values()){
				for(ShortGenresEnum shortGenresEnum : ShortGenresEnum.values()){
					try{
						String requestUrl = this.achieveShortListReqeustUrl(shortListUrl, shortGenresEnum.getValue(), sourceEnum.getValue());
						String jsonContent = KHttpClientUtil.doGet(requestUrl);
						JSONObject shortJsonObject = JSON.parseObject(jsonContent);
						JSONArray shortList = shortJsonObject.getJSONObject("data").getJSONArray("short_list");
						if(KCollectionUtils.isEmpty(shortList)) continue;
						
						for(int i = 0;i<shortList.size();i++){
							JSONObject jsonObject = shortList.getJSONObject(i);
							String id = jsonObject.getString("id");
							Object obj = redisUtil.get(RedisKeyConstant.SUBORIGIN_INDEX_PREFIX+id);
							String srcId = jsonObject.getString("src_id");
							String source = jsonObject.getString("source");
							String title = jsonObject.getString("title");
							logger.info("********短视频：《"+title+"》开始入库***********");
							if(obj!=null){
								logger.info("****短视频:"+title+"已经存在****");
								continue;
							}
							Integer duration = jsonObject.getInteger("duration");
							String originId = jsonObject.getString("origin_id");
							String tags = jsonObject.getString("tags");
							String genres = jsonObject.getString("genres");
							String playUrl = jsonObject.getString("play_url");
							String imgUrl = jsonObject.getString("img_url");
							
							//设置短视频的基本信息
							KVContentEntity content = new KVContentEntity();
							content.setIsFree(IsFreeEnum.YES.getValue());
							content.setOriginId(originId);
							content.setSource(source);
							content.setShortFlag(ShortFlagEnum.SHORT.getValue());
							content.setSubOriginId(id);
							content.setCreateTime(new Date());
							content.setTitle(title);
							content.setDuration(duration);
							content.setTags(tags);
							content.setImgLargeUrl(imgUrl);
							content.setSource(source);
							content.setGenres(genres);
							content.setStatus(StatusEnum.VALID.getValue());
							kvContentMapper.insert(content);
							
							//设置短时内容源
							KVContentSourceEntity contentSource = new KVContentSourceEntity();
							contentSource.setCreateTime(new Date());
							contentSource.setDuration(duration==null?"":duration.toString());
							contentSource.setSourceId(srcId);
							contentSource.setSource(source);
							contentSource.setPlayUrl(playUrl);
							contentSource.setImageUrl(imgUrl);
							contentSource.setRelativeId(content.getId());
							contentSource.setRelativeType("k_video_content");
							contentSource.setIsFree(IsFreeEnum.YES.getValue());
							contentSource.setStatus(StatusEnum.VALID.getValue());
							kvContentSourceMapper.insert(contentSource);
							
							redisUtil.set(RedisKeyConstant.SUBORIGIN_INDEX_PREFIX+id, id);
							logger.info("********短视频：《"+title+"》入库成功***********");
						}
					}catch(Exception e){
						e.printStackTrace();
						logger.info("****出现未知异常****");
						continue;
					}
				}
			}	
			return true;
		}

		@Override
		public PageInfo<KVContentEntity> queryKvContentByPage(Integer shortFlag,String id, String title, Integer status,
				Integer pageIndex, Integer pageSize) {
			PageHelper.startPage(pageIndex,pageSize, true, null, true);
			List<KVContentEntity> entitys = new ArrayList<KVContentEntity>();
			entitys = kvContentMapper.queryKvContentByPage(shortFlag,id,title,status);
			PageInfo<KVContentEntity> page = new PageInfo<KVContentEntity>(entitys);
			return page;
		}

		@Override
		public KVContentEntity querykvContentById(String contentId) {
			KVContentEntity kvContentEntity = kvContentMapper.selectByPrimaryKey(contentId);
			if(kvContentEntity == null) logger.error(String.format("根据内容id：s%没有查询出对应的内容实体类", kvContentEntity));
			if(ShortFlagEnum.LONG.getValue() ==kvContentEntity.getShortFlag()){//设置长视频播放剧集以及地址
				List<KVContentItemEntity> kvContentItemEntities = kvContentItemMapper.queryItemByContentId(kvContentEntity.getId());
				if(KCollectionUtils.isEmpty(kvContentItemEntities)) logger.info(String.format("%s没有找到相关的剧集以及播放地址", kvContentEntity.getTitle()));
				
				for(KVContentItemEntity kvContentItemEntity : kvContentItemEntities){
					List<KVContentSourceEntity> kvContentSources = kvContentSourceMapper.findByRelativeTypeAndRelativeId(Constants.TABLE_K_VIDEO_ITEM, kvContentItemEntity.getId());
					kvContentItemEntity.setContentSourceList(kvContentSources);
				}
				//设置剧集内容
				kvContentEntity.setListContentItems(kvContentItemEntities);
				
			} else if (ShortFlagEnum.SHORT.getValue() ==kvContentEntity.getShortFlag()){//设置短视频播放地址
				
				List<KVContentSourceEntity> contentSourceEntities = kvContentSourceMapper.findByRelativeTypeAndRelativeId(Constants.TABLE_K_VIDEO_CONTENT,kvContentEntity.getId());
				//设置播放地址
				kvContentEntity.setListContentSources(contentSourceEntities);
			}
			return kvContentEntity;
		}

		@Override
		public void saveUpdateKVContent(Map<String, Object> params) {
			if(params.get("contentId")!=null){//更新
				KVContentEntity kvContentEntity = kvContentMapper.selectByPrimaryKey(params.get("contentId").toString());
				if(kvContentEntity == null) logger.error("id:"+params.get("contentId").toString()+"不存在");
				
				
				
				
			}else{//新增
				
			}
		}
}
