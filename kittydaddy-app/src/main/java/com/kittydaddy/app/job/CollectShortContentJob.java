package com.kittydaddy.app.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kittydaddy.common.enums.ShortGenresEnum;
import com.kittydaddy.common.enums.SourceEnum;
import com.kittydaddy.common.utils.KCollectionUtils;
import com.kittydaddy.common.utils.KHttpClientUtil;
import com.kittydaddy.metadata.util.RedisUtil;
import com.kittydaddy.metadata.vcontent.domain.KVContentEntity;

/**
 * @author kitty daddy
 * 采集短视频定时任务
 */
@Component
public class CollectShortContentJob {
	private static final Logger logger = LoggerFactory.getLogger(CollectShortContentJob.class);
	
	@Value("${kitty.video.short.content.list}")
	private String shortListUrl;
	
	@Autowired
	private RedisUtil redisUtil;
	
	//设置起始页
	private Integer start = 0;
		
	@Scheduled(cron = "0 16 14 ? * *")
    public void collectShortJob(){
		  logger.info("*******开始获取短视频信息********");
    	  while(start >= 0){
    		  if(execute()){ 
    			  start += 100;
              }else{
            	  break;
              }
    	  }
    	  logger.info("*****短视频任务获取结束*****");
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
				String jsonContent = KHttpClientUtil.doGet(requestUrl);
				JSONObject shortJsonObject = JSON.parseObject(jsonContent);
				JSONArray shortList = shortJsonObject.getJSONObject("data").getJSONArray("short_list");
				if(KCollectionUtils.isEmpty(shortList)) continue;
				
				for(int i = 0;i<shortList.size();i++){
					JSONObject jsonObject = shortList.getJSONObject(i);
					String id = jsonObject.getString("id");
					String srcId = jsonObject.getString("src_id");
					String source = jsonObject.getString("source");
					String title = jsonObject.getString("title");
					String duration = jsonObject.getString("duration");
					String originId = jsonObject.getString("origin_id");
					String tags = jsonObject.getString("tags");
					String genres = jsonObject.getString("genres");
					String playUrl = jsonObject.getString("play_url");
					String imgUrl = jsonObject.getString("img_url");
					
					
					KVContentEntity kv = new KVContentEntity();
					
				}
				
			}
		}	
		return true;
	}
	
}
