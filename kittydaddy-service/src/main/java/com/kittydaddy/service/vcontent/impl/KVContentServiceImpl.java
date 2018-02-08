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
import com.kittydaddy.common.utils.KBeanUtil;
import com.kittydaddy.common.utils.KCollectionUtils;
import com.kittydaddy.common.utils.KHttpClientUtil;
import com.kittydaddy.common.utils.KIntegerUtil;
import com.kittydaddy.common.utils.KJSONPParser;
import com.kittydaddy.common.utils.KStringUtils;
import com.kittydaddy.metadata.pvcontent.dao.PContentEntityMapper;
import com.kittydaddy.metadata.pvcontent.dao.PContentItemEntityMapper;
import com.kittydaddy.metadata.pvcontent.dao.PContentSourceEntityMapper;
import com.kittydaddy.metadata.pvcontent.domain.PContentEntity;
import com.kittydaddy.metadata.pvcontent.domain.PContentItemEntity;
import com.kittydaddy.metadata.pvcontent.domain.PContentSourceEntity;
import com.kittydaddy.metadata.util.RedisUtil;
import com.kittydaddy.metadata.vcontent.dao.KVContentEntityMapper;
import com.kittydaddy.metadata.vcontent.dao.KVContentItemEntityMapper;
import com.kittydaddy.metadata.vcontent.dao.KVContentSourceEntityMapper;
import com.kittydaddy.metadata.vcontent.domain.KVContentEntity;
import com.kittydaddy.metadata.vcontent.domain.KVContentItemEntity;
import com.kittydaddy.metadata.vcontent.domain.KVContentSourceEntity;
import com.kittydaddy.search.model.pvcontent.PublishContentEntity;
import com.kittydaddy.search.repository.pvcontent.PublishContentEntityRepository;
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
	
	@Autowired
	private PContentEntityMapper pContentMapper;
	
	@Autowired
	private PContentItemEntityMapper pContentItemMapper;
	
	@Autowired
	private PContentSourceEntityMapper pContentSourceMapper;
	
	@Autowired
	private PublishContentEntityRepository publishContentRepository;
	
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
		Integer subOriginId = 1;
		Object object = redisUtil.get(RedisKeyConstant.SUBORIGIN_INDEX_PREFIX);
		if(object!=null) subOriginId = (Integer)object;
    	for(; subOriginId<999999999; subOriginId++){
    		logger.info("****获取第："+subOriginId+"条********");
    		if(!this.executeCollectSingleVideoService(subOriginId.toString())) continue;
    		redisUtil.set(RedisKeyConstant.SUBORIGIN_INDEX_PREFIX, subOriginId);
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
				 redisUtil.set(RedisKeyConstant.SUBORIGIN_INDEX_PREFIX, subOriginId);
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
					String requestUrl = this.achieveShortListReqeustUrl(shortListUrl, shortGenresEnum.getValue(), sourceEnum.getValue());
					try{
						String jsonContent = KHttpClientUtil.doGet(requestUrl);
						JSONObject shortJsonObject = JSON.parseObject(jsonContent);
						JSONArray shortList = shortJsonObject.getJSONObject("data").getJSONArray("short_list");
						if(KCollectionUtils.isEmpty(shortList)) continue;
						for(int i = 0;i<shortList.size();i++){
							JSONObject jsonObject = shortList.getJSONObject(i);
							String id = jsonObject.getString("id");
							String title = jsonObject.getString("title");
							Object obj = redisUtil.get(RedisKeyConstant.SHORT_SUBORIGIN_INDEX_PREFIX+id);
							if(obj != null){
								logger.info("**"+title+"已经存在**");
								continue;
							} 
							String srcId = jsonObject.getString("src_id");
							String source = jsonObject.getString("source");
							logger.info("********短视频：《"+title+"》开始入库***********");
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
							content.setChannel("短视频");
							content.setEpisodeCount(1);
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
							
							redisUtil.set(RedisKeyConstant.SHORT_SUBORIGIN_INDEX_PREFIX+id,id);
							logger.info("********短视频：《"+title+"》入库成功***********");
						}
					}catch(Exception e){
						e.printStackTrace();
						logger.info("****出现未知异常****");
						logger.info("****请求的url地址为:"+requestUrl);
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
			String title = params.get("title")==null?"": params.get("title").toString();
			String subtitle = params.get("subtitle")==null?"":params.get("subtitle").toString();
			String source = params.get("source")==null?"":params.get("source").toString();
			String rate = params.get("rate")==null?"":params.get("rate").toString();
			String channel = params.get("channel")==null?"":params.get("channel").toString();
			String shortFlag = params.get("shortFlag")==null?"":params.get("shortFlag").toString();
			String tags = params.get("tags")==null?"":params.get("tags").toString();
			String episodeCount = params.get("episodeCount")==null?"":params.get("episodeCount").toString();
			String directors = params.get("directors")==null?"":params.get("directors").toString();
			String actors = params.get("actors")==null?"":params.get("actors").toString();
			String year = params.get("year")==null?"":params.get("year").toString();
			String lastSn = params.get("lastSn")==null?"":params.get("lastSn").toString();
			String duration = params.get("duration")==null?"":params.get("duration").toString();
			String area = params.get("area")==null?"":params.get("area").toString();
            String language = params.get("language") ==null?"":params.get("language").toString();
            String isFree = params.get("isFree")==null?"":params.get("isFree").toString();
			String imgSmallUrl = params.get("imgSmallUrl")==null?"":params.get("imgSmallUrl").toString();
			String imgMediumUrl = params.get("imgMediumUrl")==null?"":params.get("imgMediumUrl").toString();
			String imgLargeUrl = params.get("imgLargeUrl")==null?"":params.get("imgLargeUrl").toString();
			String isPublish = params.get("isPublish")==null?"":params.get("isPublish").toString();
			String summary = params.get("summary")==null?"":params.get("summary").toString();
			
			if(params.get("contentId")!=null){//更新
				KVContentEntity kvContentEntity = kvContentMapper.selectByPrimaryKey(params.get("contentId").toString());
				if(kvContentEntity == null) logger.error("id:"+params.get("contentId").toString()+"不存在");
				kvContentEntity.setActors(actors);
				kvContentEntity.setArea(area);
				kvContentEntity.setChannel(channel);
				kvContentEntity.setUpdateTime(new Date());
				kvContentEntity.setDirectors(directors);
				kvContentEntity.setDuration(params.get("duration")==null?kvContentEntity.getDuration():Integer.parseInt(duration));
				kvContentEntity.setEpisodeCount(params.get("episodeCount")==null?kvContentEntity.getEpisodeCount():KIntegerUtil.str2Integer(episodeCount));
				kvContentEntity.setImgLargeUrl(imgLargeUrl);
				kvContentEntity.setImgMediumUrl(imgMediumUrl);
				kvContentEntity.setImgSmallUrl(imgSmallUrl);
				kvContentEntity.setIsFree(params.get("isFree")==null?kvContentEntity.getIsFree():Integer.parseInt(isFree));
				kvContentEntity.setLanguage(language);
				kvContentEntity.setLastSn(params.get("lastSn")==null?kvContentEntity.getLastSn():KIntegerUtil.str2Integer(lastSn));
				kvContentEntity.setOperateId(params.get("operateId").toString());
				kvContentEntity.setRate(rate);
				kvContentEntity.setSource(source);
				kvContentEntity.setSubtitle(subtitle);
				kvContentEntity.setIsPublish(PublishStatusEnum.NO.getValue());
				kvContentEntity.setSummary(summary);
				kvContentEntity.setTags(tags);
				kvContentEntity.setTitle(title);
                kvContentEntity.setYear(year);
                kvContentMapper.updateByPrimaryKey(kvContentEntity);
                
			}else{//新增
				
			}
		}

		@Override
		public boolean publishContent(String contentId, String operateId) throws Exception {
			/***1、发布到前端表******/
			PContentEntity pContentEntity = pContentMapper.selectByPrimaryKey(contentId);
			KVContentEntity kvContentEntity = kvContentMapper.selectByPrimaryKey(contentId);
			if(pContentEntity == null) {
				pContentEntity = new PContentEntity();
				KBeanUtil.copy(kvContentEntity, pContentEntity);
				pContentMapper.insert(pContentEntity);
			}else{
				KBeanUtil.copy(kvContentEntity, pContentEntity);
				pContentMapper.updateByPrimaryKey(pContentEntity);
			}
			
			if(kvContentEntity.getShortFlag()==ShortFlagEnum.SHORT.getValue()){//发布短视频
				List<PContentSourceEntity> shortSourceEntities = pContentSourceMapper.findSourceByRelativeTypeAndRelativeId(Constants.TABLE_K_VIDEO_CONTENT,contentId);
				List<KVContentSourceEntity> kvContentSourceEntities = kvContentSourceMapper.findByRelativeTypeAndRelativeId(Constants.TABLE_K_VIDEO_CONTENT, contentId);
				if(!existFreeSource(kvContentSourceEntities))return false;
				if(KCollectionUtils.isEmpty(shortSourceEntities)) return false;
				for(PContentSourceEntity pContentSourceEntity : shortSourceEntities){
					pContentSourceMapper.deleteByPrimaryKey(pContentSourceEntity.getId());
					
					PContentSourceEntity pContentSource = new PContentSourceEntity();
					KBeanUtil.copy(pContentSourceEntity, pContentSource);
					pContentSourceMapper.insert(pContentSource);
				}
				
			}else if(kvContentEntity.getShortFlag()==ShortFlagEnum.LONG.getValue()){//发布长视频
				KBeanUtil.copy(kvContentEntity, pContentEntity);
				pContentEntity.setOperateId(operateId);
				pContentMapper.updateByPrimaryKey(pContentEntity);
				
				//删除原来的剧集，新增新的剧集
				List<PContentItemEntity> pItemEntities = pContentItemMapper.findItemByContentId(contentId);
				if(KCollectionUtils.isNotEmpty(pItemEntities)){
					for(PContentItemEntity pItemEntity : pItemEntities){
						pContentItemMapper.deleteByPrimaryKey(pItemEntity.getId());
					}
				}
				List<KVContentItemEntity> kvItemEntities = kvContentItemMapper.queryItemByContentId(contentId);
				if(KCollectionUtils.isEmpty(kvItemEntities)) return false;
				for(KVContentItemEntity itemEntity : kvItemEntities){
					PContentItemEntity pItemEntity = new PContentItemEntity();
					KBeanUtil.copy(itemEntity, pItemEntity);
					pContentItemMapper.insert(pItemEntity);
					
					List<PContentSourceEntity> contentSourceEntities = pContentSourceMapper.findSourceByRelativeTypeAndRelativeId(Constants.TABLE_K_VIDEO_ITEM,pItemEntity.getId());
					if(KCollectionUtils.isNotEmpty(contentSourceEntities)){
						for(PContentSourceEntity contentSourceEntity : contentSourceEntities){
							 pContentSourceMapper.deleteByPrimaryKey(contentSourceEntity.getId());
						}
					}
					
					List<KVContentSourceEntity> contentSources = kvContentSourceMapper.findByRelativeTypeAndRelativeId(Constants.TABLE_K_VIDEO_ITEM, itemEntity.getId());
					if(KCollectionUtils.isEmpty(contentSources)) return false;
					if(!existFreeSource(contentSources)) return false;
					for(KVContentSourceEntity contentSource : contentSources){
						  PContentSourceEntity pContentSource = new PContentSourceEntity();
						  KBeanUtil.copy(contentSource, pContentSource);
						  pContentSourceMapper.insert(pContentSource);
					}
				}
			}
			
			/***2、发布到搜索引擎****/
			PublishContentEntity publishContentEntity = publishContentRepository.findOne(contentId);
			if(publishContentEntity == null) {
				publishContentEntity = new PublishContentEntity();
			}else{
				publishContentRepository.delete(publishContentEntity);
			};
            
			KVContentEntity contentEntity = kvContentMapper.selectByPrimaryKey(contentId);
			KBeanUtil.copy(contentEntity, publishContentEntity);
			
			if(ShortFlagEnum.LONG.getValue() == contentEntity.getShortFlag()){//发布长视频
				List<KVContentItemEntity> contentItemEntities = kvContentItemMapper.queryItemByContentId(contentId);
				if(KCollectionUtils.isEmpty(contentItemEntities)) return false;
				for(KVContentItemEntity itemEntity : contentItemEntities){
					List<KVContentSourceEntity> sources = kvContentSourceMapper.findByRelativeTypeAndRelativeId(Constants.TABLE_K_VIDEO_ITEM, itemEntity.getId());
					itemEntity.setContentSourceList(sources);
				}
				publishContentEntity.setContentItems(contentItemEntities);
				
			}else if(ShortFlagEnum.SHORT.getValue() == contentEntity.getShortFlag()){//发布短视频
				List<KVContentSourceEntity> contentSourceEntities = kvContentSourceMapper.findByRelativeTypeAndRelativeId(Constants.TABLE_K_VIDEO_CONTENT, contentId);
				if(KCollectionUtils.isEmpty(contentSourceEntities)) return false;
				publishContentEntity.setContentSources(contentSourceEntities);
			}
			publishContentRepository.save(publishContentEntity);
			
			/***3、更新发布状态****/
			kvContentEntity.setIsPublish(PublishStatusEnum.YES.getValue());
			kvContentEntity.setPublishTime(new Date());
			kvContentMapper.updateByPrimaryKey(kvContentEntity);
			return true;
		}
		
		
		/**
		 * 判断source中是否存在免费源
		 * @param contentSources
		 * @return
		 */
		private boolean existFreeSource(List<KVContentSourceEntity> contentSources){
			boolean flag = false;
			for(KVContentSourceEntity contentSourceEntity : contentSources){
				if(IsFreeEnum.YES.getValue() == contentSourceEntity.getIsFree()){
					 flag = true;
					 break;
				}
			}
			return flag;
		}
}
